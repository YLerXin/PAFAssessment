package vttp.batch5.paf.movies.services;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.json.data.JsonDataSource;
import net.sf.jasperreports.pdf.JRPdfExporter;
import net.sf.jasperreports.pdf.SimplePdfExporterConfiguration;
import net.sf.jasperreports.pdf.SimplePdfReportConfiguration;
import vttp.batch5.paf.movies.models.DirectorResults;
import vttp.batch5.paf.movies.models.Movies;
import vttp.batch5.paf.movies.repositories.MongoMovieRepository;
import vttp.batch5.paf.movies.repositories.MySQLMovieRepository;

@Service
public class MovieService {
  @Autowired
  private MySQLMovieRepository SQLRepo;

  @Autowired
  private MongoMovieRepository MongoRepo;

  // TODO: Task 2


  @Transactional
  public void updateDatabases(List<Movies> movies) throws Exception{
    try{
      System.out.println("Trying to update database");
      SQLRepo.batchInsertMovies(movies);
      MongoRepo.batchInsertMovies(movies);
    }catch(Exception e){
      List<String> ids = movies.stream()
      .map(Movies::getImdb_id)
      .filter(id -> id != null && !id.isBlank())
      .toList();
      MongoRepo.logError(ids,e.getMessage());
      throw e;
    }
  }

  // TODO: Task 3
  // You may change the signature of this method by passing any number of parameters
  // and returning any type
  public JsonArray getProlificDirectors(int n) {

    List<Document> mongoMovies = MongoRepo.findAllMovies();
    System.out.println("1--------------------------"+ mongoMovies.size());

    List<Movies> sqlMovies = SQLRepo.findAllSQLMovies();
    System.out.println("2--------------------------"+ sqlMovies.size());

    Map<String,Movies> sqlMap = new HashMap<>();
    for (Movies m: sqlMovies){
      sqlMap.put(m.getImdb_id(),m);
    }
    System.out.println("2.5--------------------------"+ sqlMap.size());

    Map<String,DirectorResults> directorMap = new HashMap<>();

    for (Document doc : mongoMovies){
      String imdbID = doc.getString("imdb_id");
      String directorsResult = doc.getString("directors");
      if(directorsResult == null){
        continue;
      }
          //bloody multiple directors
      String[] directorArr = directorsResult.split(",");

      Movies sqlData = sqlMap.get(imdbID);
      int revenue = 0;
      int budget = 0;
      if (sqlData != null){
        revenue = sqlData.getRevenue();
        budget = sqlData.getBudget();
      }

      for (String d : directorArr){
        String name = d.trim();
        if(name.isEmpty()){
          System.out.println("3--------------------------"+ imdbID);

          continue;
        }
        DirectorResults dr = directorMap.getOrDefault(name, new DirectorResults(name));
        dr.Count += 1;
        dr.totalRev += revenue;
        dr.totalBud += budget;
        directorMap.put(name,dr);
      }
    }
//Collections.sort(list, (a, b) -> Integer.compare(a[1], b[1]));

    List<DirectorResults> sortedResults = new ArrayList<>(directorMap.values());
    sortedResults.sort((a,b)->Integer.compare(b.Count,a.Count));
    System.out.println("4--------------------------"+ sortedResults.size());

    List<DirectorResults> topN = sortedResults.subList(0,Math.min(n,sortedResults.size()));
    System.out.println("5--------------------------"+ " directors.");


    JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
    for(DirectorResults dr : topN){
      //long profitloss = dr.totalRev - dr.totalBud;
      JsonObjectBuilder obj = Json.createObjectBuilder()
      .add("director_name",dr.Dname)
      .add("movie_count",dr.Count)
      .add("total_revenue",dr.totalRev)
      .add("total_budget",dr.totalBud);
      arrBuilder.add(obj);
    }
    JsonArray result = arrBuilder.build();
    System.out.println("6--------------------------"+ result.size());
    return result;

  }


  // TODO: Task 4
  // You may change the signature of this method by passing any number of parameters
  // and returning any type
  public String getHeaderJson(String myName, String batch) {
    return Json.createObjectBuilder()
        .add("my_name", myName)
        .add("batch", batch)
        .build()
        .toString();
}
public String getDirectorJsonArray(int n){
  JsonArray arr = getProlificDirectors(n);
  return arr.toString();
}

  public void generatePDFReport(String myName,String batch,int limit) throws Exception{
    String headersJson = getHeaderJson(myName, batch);
    String directorJsonArray = getDirectorJsonArray(limit);

    JsonDataSource reportDS = new JsonDataSource(new ByteArrayInputStream(headersJson.getBytes(StandardCharsets.UTF_8)));
    JsonDataSource directorDS = new JsonDataSource(new ByteArrayInputStream(directorJsonArray.getBytes(StandardCharsets.UTF_8)));

    Map<String,Object> params = new HashMap<>();
    params.put("DIRECTOR_TABLE_DATASET",directorDS);

    JasperReport report =(JasperReport) JRLoader.loadObjectFromFile("/data/director_movies_report.jasper");


    JasperPrint print = JasperFillManager.fillReport(report,params,reportDS);

    JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("director_movies_report.pdf"));

        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);

        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setMetadataAuthor("MyName");
        exportConfig.setEncrypted(false);

        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);
        exporter.exportReport();
  }

}

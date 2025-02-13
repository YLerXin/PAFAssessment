package vttp.batch5.paf.movies.bootstrap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonStructure;
import jakarta.json.JsonValue;
import jakarta.json.JsonValue.ValueType;
@Component
public class Dataloader {
  private JsonArray allMovies;

  //TODO: Task 2
  public void load_data(String location) throws IOException {
    String jsonText = null;
    try (ZipFile zipFile = new ZipFile(location)) {
      Enumeration<? extends ZipEntry> entries = zipFile.entries();
      while (entries.hasMoreElements()) {
          ZipEntry entry = entries.nextElement();
          if (!entry.isDirectory() && entry.getName().endsWith(".json")) {
              try (InputStream is = zipFile.getInputStream(entry);
                   ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                  byte[] buffer = new byte[4096];
                  int bytesRead;
                  while ((bytesRead = is.read(buffer)) != -1) {
                      bos.write(buffer, 0, bytesRead);
                  }
                  jsonText = bos.toString(StandardCharsets.UTF_8);
              }
              break;
          }
      }
  }
  JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    if (jsonText != null) {
      String[] lines = jsonText.split("\n");
      for (String line : lines) {
          line = line.trim();
          if (!line.isEmpty()) {
              try (JsonReader reader = Json.createReader(new StringReader(line))) {
                  JsonStructure structure = reader.read();
                  if (structure.getValueType() == ValueType.OBJECT) {
                      arrayBuilder.add(structure.asJsonObject());
                  }
              }
          }
      }
  }
    allMovies = arrayBuilder.build();
  
  }

/*   public JsonArray filter_data() {
    JsonArrayBuilder builder = Json.createArrayBuilder();
    for (JsonValue v : allMovies) {
        if (v.getValueType() == ValueType.OBJECT) {
            JsonObject o = v.asJsonObject();
            String rd = o.getString("release_date", "");
            if (rd.length() >= 4) {
                try {
                    int y = Integer.parseInt(rd.substring(0, 4)); //get 2012-04-12" the 2012 part of it
                    if (y >= 2018) {
                        builder.add(Json.createObjectBuilder()//this also filters
                            .add("title", o.containsKey("title") ? o.getString("title") : "")
                            .add("vote_average", o.containsKey("vote_average") ? o.getInt("vote_average") : 0)
                            .add("vote_count", o.containsKey("vote_count") ? o.getInt("vote_count") : 0)
                            .add("status", o.containsKey("status") ? o.getString("status") : "")
                            .add("release_date", rd)
                            .add("revenue", o.containsKey("revenue") ? o.getInt("revenue") : 0)
                            .add("runtime", o.containsKey("runtime") ? o.getInt("runtime") : 0)
                            .add("budget", o.containsKey("budget") ? o.getInt("budget") : 0)
                            .add("imdb_id", o.containsKey("imdb_id") ? o.getString("imdb_id") : "")
                            .add("original_language", o.containsKey("original_language") ? o.getString("original_language") : "")
                            .add("overview", o.containsKey("overview") ? o.getString("overview") : "")
                            .add("popularity", o.containsKey("popularity") ? o.getInt("popularity") : 0)
                            .add("tagline", o.containsKey("tagline") ? o.getString("tagline") : "")
                            .add("genres", o.containsKey("genres") ? o.getString("genres") : "")
                            .add("spoken_languages", o.containsKey("spoken_languages") ? o.getString("spoken_languages") : "")
                            .add("casts", o.containsKey("casts") ? o.getString("casts") : "")
                            .add("director", o.containsKey("director") ? o.getString("director") : "")
                            .add("imdb_rating", o.containsKey("imdb_rating") ? o.getInt("imdb_rating") : 0)
                            .add("imdb_votes", o.containsKey("imdb_votes") ? o.getInt("imdb_votes") : 0)
                            .add("poster_path", o.containsKey("poster_path") ? o.getString("poster_path") : "")
                        );
                    }
                } catch (NumberFormatException ex) {
                  System.err.println("DEBUG FILTER: NumberFormatException for release_date -> " + rd);

                }
            }
        }
    }
    return builder.build();
} */
public JsonArray filter_data() {
  JsonArrayBuilder builder = Json.createArrayBuilder();
  for (JsonValue v : allMovies) {
      if (v.getValueType() == ValueType.OBJECT) {
          JsonObject o = v.asJsonObject();
          String rd = o.getString("release_date", "");
          if (rd.length() >= 4) {
              try {
                  int y = Integer.parseInt(rd.substring(0, 4));
                  if (y >= 2018) {
                      builder.add(Json.createObjectBuilder()
                          .add("title", o.containsKey("title") ? o.getString("title") : "")
                          .add("vote_average", getSafeInt(o, "vote_average"))
                          .add("vote_count", getSafeInt(o, "vote_count"))
                          .add("status", o.containsKey("status") ? o.getString("status") : "")
                          .add("release_date", rd)
                          .add("revenue", getSafeInt(o, "revenue"))
                          .add("runtime", getSafeInt(o, "runtime"))
                          .add("budget", getSafeInt(o, "budget"))
                          .add("imdb_id", o.containsKey("imdb_id") ? o.getString("imdb_id") : "")
                          .add("original_language", o.containsKey("original_language") ? o.getString("original_language") : "")
                          .add("overview", o.containsKey("overview") ? o.getString("overview") : "")
                          .add("popularity", getSafeInt(o, "popularity"))
                          .add("tagline", o.containsKey("tagline") ? o.getString("tagline") : "")
                          .add("genres", o.containsKey("genres") ? o.getString("genres") : "")
                          .add("spoken_languages", o.containsKey("spoken_languages") ? o.getString("spoken_languages") : "")
                          .add("casts", o.containsKey("casts") ? o.getString("casts") : "")
                          .add("director", o.containsKey("director") ? o.getString("director") : "")
                          .add("imdb_rating", getSafeInt(o, "imdb_rating"))
                          .add("imdb_votes", getSafeInt(o, "imdb_votes"))
                          .add("poster_path", o.containsKey("poster_path") ? o.getString("poster_path") : "")
                      );
                  }
              } catch (Exception ex) {
                System.err.println(ex);
              }
          }
      }
  }
  return builder.build();
}
//stupid format error somewhere
private int getSafeInt(JsonObject o, String fieldName) {
  if (!o.containsKey(fieldName)) {
      return 0;
  }
  JsonValue val = o.get(fieldName);
  switch (val.getValueType()) {
      case NUMBER:
          return o.getJsonNumber(fieldName).intValue();
      case STRING:
          try {
              return (int) Double.parseDouble(o.getString(fieldName));
          } catch (Exception e) {
              return 0;
          }
      default:
          return 0;
  }
}
}




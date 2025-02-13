package vttp.batch5.paf.movies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import vttp.batch5.paf.movies.bootstrap.Dataloader;
import vttp.batch5.paf.movies.models.Movies;
import vttp.batch5.paf.movies.services.MovieService;

@SpringBootApplication
public class MoviesApplication implements CommandLineRunner {
	@Autowired
	private Dataloader dataloader;
	@Autowired
	private MovieService movieService;

	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}

	@Override
	public void run(String... args){
		try{
            dataloader.load_data("data/movies_post_2010.zip");

			//dataloader.load_data("C:/Users/yongl/VTTP/PAF/paf_b5_assessment_template/data/movies_post_2010.zip");

            var filtered = dataloader.filter_data();
	
            List<Movies> movieList = new ArrayList<>();
            for (JsonValue val : filtered) {
                if (val.getValueType() == JsonValue.ValueType.OBJECT) {
                    JsonObject obj = val.asJsonObject();
                    Movies m = new Movies();
                    m.setImdb_id(obj.getString("imdb_id", ""));
                    m.setVote_average(obj.getInt("vote_average", 0));
                    m.setVote_count(obj.getInt("vote_count", 0));
                    m.setRelease_date(obj.getString("release_date", ""));
                    m.setRevenue(obj.getInt("revenue", 0));
                    m.setBudget(obj.getInt("budget", 0));
                    m.setRuntime(obj.getInt("runtime", 0));
                    m.setTitle(obj.getString("title", ""));
                    m.setDirector(obj.getString("director", ""));
                    m.setOverview(obj.getString("overview", ""));
                    m.setTagline(obj.getString("tagline", ""));
                    m.setGenres(obj.getString("genres", ""));
                    m.setImdb_rating(obj.getInt("imdb_rating", 0));
                    m.setImdb_votes(obj.getInt("imdb_votes", 0));
                    movieList.add(m);
                }
            }

            movieService.updateDatabases(movieList);
        } catch (Exception e) {
            System.err.println("Transaction rolled back: " + e.getMessage());
			e.printStackTrace();

        }
    }
}

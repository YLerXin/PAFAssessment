package vttp.batch5.paf.movies.repositories;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonArrayBuilder;
import vttp.batch5.paf.movies.models.Movies;

import static vttp.batch5.paf.movies.models.Movies.*;
@Repository
public class MongoMovieRepository {
    @Autowired
    private MongoTemplate template;

 // TODO: Task 2.3
 // You can add any number of parameters and return any type from the method
 // You can throw any checked exceptions from the method
 // Write the native Mongo query you implement in the method in the comments
 //
 //    native MongoDB query here
/*         db.imdb.insertMany([
            imdb_id:"",title:"",.....
            ]) */

 //
 public List<Movies> batchInsertMovies(List<Movies> movies) {
    List<Document> docs = new ArrayList<>();
    for (Movies m : movies){
        Document doc = new Document()
        .append("imdb_id",m.getImdb_id())
        .append("title",m.getTitle())
        .append("directors", m.getDirector())
        .append("overview",m.getOverview())
        .append("tagline",m.getTagline())
        .append("genres",m.getGenres())
        .append("imdb_rating",m.getImdb_rating())
        .append("imdb_votes",m.getImdb_votes());
        docs.add(doc);
    }
    template.insert(docs,"imdb");
    return movies;
 }

 // TODO: Task 2.4
 // You can add any number of parameters and return any type from the method
 // You can throw any checked exceptions from the method
 // Write the native Mongo query you implement in the method in the comments
 //
 //    native MongoDB query here
    /*  db.error.insert([
        {imdb_ids:[],error:"..".....}
        ]) */ 
 //
    public void logError(List<String> ids, String errorMessage) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
            .add("timestamp", Instant.now().toString());
        if (ids != null && !ids.isEmpty()) {
            builder.add("imdb_ids", Json.createArrayBuilder(ids));
        } else {
            builder.add("imdb_ids", Json.createArrayBuilder());
        }
        builder.add("error", errorMessage);

        Document doc = Document.parse(builder.build().toString());
        template.insert(doc, "errors");
    }


 // TODO: Task 3
 // Write the native Mongo query you implement in the method in the comments
 //
 //    native MongoDB query here
        /* db.imdb.find({}) */
 //
 //since only the following are in mongo i extract everything
/* 
imdb_id
title
directors
overview
tagline
imdb_rating
imdb_votes */ 
    public List<Document> findAllMovies(){
        return template.findAll(Document.class,"imdb");
    }


}

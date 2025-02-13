package vttp.batch5.paf.movies.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.JsonArray;
import vttp.batch5.paf.movies.models.Movies;
import vttp.batch5.paf.movies.services.MovieService;

@Controller
@RequestMapping
public class MainController {
@Autowired
private MovieService movieService;
  // TODO: Task 3
   //@GetMapping("/api/summary/{count}")
   @GetMapping("/api/summary")

   @ResponseBody
    //  public JsonArray getProificDirectors(@PathVariable Integer count){
    public JsonArray getProificDirectors(@RequestParam Integer count){
    JsonArray results = movieService.getProlificDirectors(count);
    return results;
   }

  
  // TODO: Task 4
   @GetMapping("/api/summary/pdf")
   @ResponseBody
   public String generateReport(@RequestParam String myName,@RequestParam String batch,@RequestParam int limit) throws Exception{
    movieService.generatePDFReport(myName, batch, limit);
    return "Success";
   }

}

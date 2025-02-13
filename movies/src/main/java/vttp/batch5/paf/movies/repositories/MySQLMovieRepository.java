package vttp.batch5.paf.movies.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.paf.movies.models.Movies;
import vttp.batch5.paf.movies.models.Movies.*;

@Repository
public class MySQLMovieRepository {
  @Autowired 
  private JdbcTemplate template;

  // TODO: Task 2.3
  // You can add any number of parameters and return any type from the method
  public static final String SQL_BATCH_INSERT = """
      INSERT INTO imdb (imdb_id,vote_average,vote_count,release_date,revenue,budget,runtime) 
      VALUES(?,?,?,?,?,?,?)
      ON DUPLICATE KEY UPDATE
    vote_average = VALUES(vote_average),
    vote_count = VALUES(vote_count),
    release_date = VALUES(release_date),
    revenue = VALUES(revenue),
    budget = VALUES(budget),
    runtime = VALUES(runtime)
      """;

  public static final String SQL_FIND_ID_CHECK ="""
      SELECT COUNT(*) from imdb where imdb_id = ?
      """;
      public int[] batchInsertMovies(List<Movies> movies) {
        List<Object[]> batchArgs = new ArrayList<>();
    for (Movies m : movies) {
      Integer count = template.queryForObject(SQL_FIND_ID_CHECK,Integer.class,m.getImdb_id());
      if (count!= null && count ==0){
        batchArgs.add(new Object[] {
          m.getImdb_id(),
          m.getVote_average(),
          m.getVote_count(),
          m.getRelease_date(),
          m.getRevenue(),
          m.getBudget(),
          m.getRuntime()
      });}
  }

  return template.batchUpdate(SQL_BATCH_INSERT, batchArgs);
}
  public static final String SQL_FIND_ALL ="""
      SELECT imdb_id,revenue,budget FROM imdb
      """;
  // TODO: Task 3
  public List<Movies> findAllSQLMovies(){
    return template.query(SQL_FIND_ALL,(rs,rowNum)->{
      Movies m = new Movies();
      m.setImdb_id(rs.getString("imdb_id"));
      m.setRevenue(rs.getInt("revenue"));
      m.setBudget(rs.getInt("budget"));
      return m;
    });
  }


}

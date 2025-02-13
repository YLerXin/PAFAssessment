package vttp.batch5.paf.movies.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Movies {
    private String title;
    private int vote_average;
    private int vote_count;
    private String status;
    private String release_date;
    private int revenue;
    private int runtime;
    private int budget;
    private String imdb_id;
    private String original_language;
    private String overview;
    private int popularity;
    private String tagline;
    private String genres;
    private String spoken_languages;
    private String casts;
    private String director;
    private int imdb_rating;
    private int imdb_votes;
    private String poster_path;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getVote_average() {
        return vote_average;
    }
    public void setVote_average(int vote_average) {
        this.vote_average = vote_average;
    }
    public int getVote_count() {
        return vote_count;
    }
    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRelease_date() {
        return release_date;
    }
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
    public int getRevenue() {
        return revenue;
    }
    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
    public int getRuntime() {
        return runtime;
    }
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }
    public int getBudget() {
        return budget;
    }
    public void setBudget(int budget) {
        this.budget = budget;
    }
    public String getImdb_id() {
        return imdb_id;
    }
    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }
    public String getOriginal_language() {
        return original_language;
    }
    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }
    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
    public int getPopularity() {
        return popularity;
    }
    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
    public String getTagline() {
        return tagline;
    }
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
    public String getGenres() {
        return genres;
    }
    public void setGenres(String genres) {
        this.genres = genres;
    }
    public String getSpoken_languages() {
        return spoken_languages;
    }
    public void setSpoken_languages(String spoken_languages) {
        this.spoken_languages = spoken_languages;
    }
    public String getCasts() {
        return casts;
    }
    public void setCasts(String casts) {
        this.casts = casts;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public int getImdb_rating() {
        return imdb_rating;
    }
    public void setImdb_rating(int imdb_rating) {
        this.imdb_rating = imdb_rating;
    }
    public Movies(String title, int vote_average, int vote_count, String status, String release_date, int revenue,
            int runtime, int budget, String imdb_id, String original_language, String overview, int popularity,
            String tagline, String genres, String spoken_languages, String casts, String director, int imdb_rating,
            int imdb_votes, String poster_path) {
        this.title = title;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.status = status;
        this.release_date = release_date;
        this.revenue = revenue;
        this.runtime = runtime;
        this.budget = budget;
        this.imdb_id = imdb_id;
        this.original_language = original_language;
        this.overview = overview;
        this.popularity = popularity;
        this.tagline = tagline;
        this.genres = genres;
        this.spoken_languages = spoken_languages;
        this.casts = casts;
        this.director = director;
        this.imdb_rating = imdb_rating;
        this.imdb_votes = imdb_votes;
        this.poster_path = poster_path;
    }
    public int getImdb_votes() {
        return imdb_votes;
    }
    public void setImdb_votes(int imdb_votes) {
        this.imdb_votes = imdb_votes;
    }
    public String getPoster_path() {
        return poster_path;
    }
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
    
    public Movies() {}



}

package com.example.movielist.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieListModel implements Serializable {

    private static final long serialVersionUID = -8959832007991513854L;

    private String vote_count;
    private String id;
    private String vote_average;
    private String title;
    private String poster_path;
    private String release_date;

    public String getVote_count() {
        return vote_count;
    }

    public String getId() {
        return id;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }


    public String getRelease_date() {
        return release_date;
    }



    // takes json movie object and returns Model object
    public static MovieListModel fromJson(JSONObject jsonObject) {
        MovieListModel ml = new MovieListModel();
        try {
            // Deserialize json into object fields
            ml.id = jsonObject.getString("id");
            ml.vote_count = jsonObject.getString("vote_count");
            ml.vote_average = jsonObject.getString("vote_average");
            ml.title = jsonObject.getString("title");
            ml.poster_path = "https://image.tmdb.org/t/p/original" + jsonObject.getString("poster_path");
            ml.release_date = jsonObject.getString("release_date");


            /*JSONArray abridgedCast = jsonObject.getJSONArray("abridged_cast");
            for (int i = 0; i < abridgedCast.length(); i++) {
                b.castList.add(abridgedCast.getJSONObject(i).getString("name"));
            }*/
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return ml;
    }

    // takes list of Json movies and return Model movies
    public static ArrayList<MovieListModel> fromJson(JSONArray jsonArray) {
        ArrayList<MovieListModel> movies = new ArrayList<MovieListModel>(jsonArray.length());
        // Process each result in json array, decode and convert to movie
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject moviesJson = null;
            try {
                moviesJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            MovieListModel movie = MovieListModel.fromJson(moviesJson);
            if (movie != null) {
                movies.add(movie);
            }
        }

        return movies;
    }




}

package com.example.movielist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.movielist.FetchAPIData.GetMovieApiData;
import com.example.movielist.Models.MovieListModel;
import com.example.movielist.Adapters.MovieListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvMovies;
    private MovieListAdapter adapterListMovie;
    public static TextView waitDataResponse;
    public static String recievedData;
    private RelativeLayout mainActivity;
    private ProgressBar spinner;


    ProgressDialog dialog;


    public static final String MOVIE_DETAIL_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        waitDataResponse = (TextView)findViewById(R.id.waitDataResponse);
        mainActivity = (RelativeLayout)findViewById(R.id.mainActivity);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);


        //call api to get list of movies
        // and I implemented my own Api key and put it in here and used this url
        GetMovieApiData.url="https://api.themoviedb.org/3/movie/popular?api_key=6053c3c13502636fb6c8e82c52487375&language=en-US&page=1";
        GetMovieApiData process = new GetMovieApiData();
        process.execute();


        spinner.setVisibility(View.VISIBLE);



        lvMovies = (ListView) findViewById(R.id.lvMovies);
        ArrayList<MovieListModel> aMovies = new ArrayList<MovieListModel>();
        adapterListMovie = new MovieListAdapter(this, aMovies);
        lvMovies.setAdapter(adapterListMovie);

        waitDataResponse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int k, int i1, int i2) {
                //Toast.makeText(getApplicationContext(), "Loading", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int k, int i1, int i2) {
                //Toast.makeText(getApplicationContext(), "i am done processing ", Toast.LENGTH_LONG).show();

                spinner.setVisibility(View.GONE);

                if(!recievedData.isEmpty()) {

                    try {
                        // recieve the object first
                        JSONObject jo = new JSONObject(recievedData);
                        int checkPageNo =  jo.getInt("page");
                        // then Get the movies json array "results"
                        JSONArray items = items = jo.getJSONArray("results");


                            // Parse json array into array of model objects
                            ArrayList<MovieListModel> movies = MovieListModel.fromJson(items);
                            //movies.clear();
                            // Load model objects into the adapter
                            for (MovieListModel movie : movies) {
                                adapterListMovie.add(movie); // add movie through the adapter
                            }

                        if(checkPageNo != 2)
                        {
                        adapterListMovie.notifyDataSetChanged();

                            GetMovieApiData.url="https://api.themoviedb.org/3/movie/popular?api_key=6053c3c13502636fb6c8e82c52487375&language=en-US&page=2";
                            GetMovieApiData process2 = new GetMovieApiData();
                            process2.execute();

                            mainActivity.setBackgroundColor(Color.parseColor("#696969"));
                        }
                        //else
                            {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                //Toast.makeText(getApplicationContext(), recievedData, Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });*/


        // on movie list click pick item id and number and sent to movie details Activity
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                // Launch the detail view passing movie as an extra
                Intent i = new Intent(MainActivity.this, MovieDetailsActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterListMovie.getItem(position));
                startActivity(i);
            }
        });

    }




}

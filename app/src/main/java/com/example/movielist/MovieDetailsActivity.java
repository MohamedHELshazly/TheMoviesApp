package com.example.movielist;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movielist.FetchAPIData.GetMovieApiData;
import com.example.movielist.FetchAPIData.GetMovieData;
import com.example.movielist.FetchAPIData.GetMovieDetailsData;
import com.example.movielist.Models.MovieDetailModel;
import com.example.movielist.Models.MovieListModel;
import com.jgabrielfreitas.core.BlurImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity {

    public static String receivedData;
    public static TextView waitMovieDetailedResponse;
    String movieId;

    ImageView mvDetailedPosterImage;
    TextView mvDetailedTitle;
    TextView mvDetailedReleaseDate;
    TextView mvDetailedViewRate;
    TextView mvStatus;
    TextView mvDetailedViewCount;
    TextView mvOverview;
    ProgressDialog dialog;
    ImageView backArrow;
    BlurImageView mvBackground;

    private ProgressBar spinner;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        waitMovieDetailedResponse = (TextView)findViewById(R.id.waitMovieDetailedResponse);
        mvDetailedPosterImage = (ImageView)findViewById(R.id.mvDetailedPosterImage);
        mvDetailedTitle =(TextView)findViewById(R.id.mvDetailedTitle);
        mvDetailedReleaseDate= (TextView) findViewById(R.id.mvDetailedReleaseDate);
        mvDetailedViewRate = (TextView) findViewById(R.id.mvDetailedViewRate);
        mvStatus = (TextView) findViewById(R.id.mvStatus);
        mvDetailedViewCount = (TextView) findViewById(R.id.mvDetailedViewCount);
        mvOverview = (TextView) findViewById(R.id.mvOverview);
        backArrow= (ImageView) findViewById(R.id.backArrow);
        spinner = (ProgressBar)findViewById(R.id.progressBar2);
        //mvBackground = (BlurImageView) findViewById(R.id.mvBackground);



        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


        // Use the movie to populate the data into our views
        MovieListModel movie = (MovieListModel)
                getIntent().getSerializableExtra(MainActivity.MOVIE_DETAIL_KEY);

         movieId = movie.getId();

        //call api to get list of movies
        GetMovieData.url="https://api.themoviedb.org/3/movie/"+movieId+"?api_key=6053c3c13502636fb6c8e82c52487375";
        GetMovieData processS = new GetMovieData();
        processS.execute();


        spinner.setVisibility(View.VISIBLE);

        waitMovieDetailedResponse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int k, int i1, int i2) {
                //Toast.makeText(getApplicationContext(), "Loading", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int k, int i1, int i2) {
                //Toast.makeText(getApplicationContext(), "i am done processing ", Toast.LENGTH_LONG).show();


                spinner.setVisibility(View.GONE);


                // recieve the object first
                try {
                    JSONObject jo = new JSONObject(receivedData);

                    MovieDetailModel movie = MovieDetailModel.fromJson(jo);

                    Picasso.get()
                            .load(movie.getPoster_path())
                            .placeholder(R.drawable.error)
                            .error(R.drawable.error)

                            .into(mvDetailedPosterImage);






                    // Populate the data into the template view using the data object
                    mvDetailedTitle.setText(movie.getTitle());
                    mvDetailedReleaseDate.setText("release date: " + movie.getRelease_date());
                    mvDetailedViewRate.setText(movie.getVote_average());
                    mvDetailedViewCount.setText(movie.getVote_count());
                    mvStatus.setText(movie.getStatus());
                    mvOverview.setText(movie.getOverview());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





    }
}

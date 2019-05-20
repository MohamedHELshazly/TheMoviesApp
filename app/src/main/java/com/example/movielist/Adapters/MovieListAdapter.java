package com.example.movielist.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movielist.Models.MovieListModel;
import com.example.movielist.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieListAdapter extends ArrayAdapter<MovieListModel> {
    public MovieListAdapter(Context context, ArrayList<MovieListModel> aMovies) {
        super(context, 0, aMovies);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO: Complete the definition of the view for each movie
        // Get the data item for this position
        MovieListModel movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
        }
        // Lookup views within item layout
        TextView mvTitle = (TextView) convertView.findViewById(R.id.mvTitle);
        TextView mvReleaseDate = (TextView) convertView.findViewById(R.id.mvReleaseDate);
        TextView mvVoteAverage = (TextView) convertView.findViewById(R.id.mvVoteAverage);
        TextView mvVoteCount = (TextView) convertView.findViewById(R.id.mvVoteCount);
        ImageView mvPosterImage = (ImageView) convertView.findViewById(R.id.mvPosterImage);

        // Populate the data into the template view using the data object
        mvTitle.setText(movie.getTitle());
        mvReleaseDate.setText("release date: " + movie.getRelease_date());
        mvVoteAverage.setText(movie.getVote_average());
        mvVoteCount.setText(movie.getVote_count());
        Picasso.get()
                .load(movie.getPoster_path())
                .placeholder(R.drawable.error)
                .error(R.drawable.error)
                .resize(150, 220)

                .into(mvPosterImage);
        // Return the completed view to render on screen
        return convertView;
    }
}

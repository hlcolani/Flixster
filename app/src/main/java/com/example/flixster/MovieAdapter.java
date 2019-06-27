package com.example.flixster;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.models.Config;
import com.example.flixster.models.Movie;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    //list of movies
    ArrayList<Movie> movies;
    //config
    Config config;
    Context context;

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    //creates and inflates a new view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //get context
       context = parent.getContext();
        //create inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        //create view using item_movie layout (our custom row layout)
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        //return that view wrapped by a ViewHolder
        return new ViewHolder(movieView);
    }

    //binds an inflated view to a new item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //get movie at specified position
        Movie movie = movies.get(position);
        //populate the view w movie info
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        //determine current orientation
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        if (isPortrait) {
            // build url for poster image
            String imageUrl = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());
            //load image using Glide
            Glide.with(context)
                    .load(imageUrl)
                    .bitmapTransform(new RoundedCornersTransformation(context, 25, 0))
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .error(R.drawable.flicks_movie_placeholder)
                    .into(viewHolder.ivPosterImage);
        } else {
            // build url for image
            String imageUrl = config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());
            //load image using Glide
            Glide.with(context)
                    .load(imageUrl)
                    .bitmapTransform(new RoundedCornersTransformation(context, 25, 0))
                    .placeholder(R.drawable.flicks_backdrop_placeholder)
                    .error(R.drawable.flicks_backdrop_placeholder)
                    .into(viewHolder.ivBackgroundImage);
        }



    }

    //returns the total number of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //create the viewholder as a static inner class
    public static class ViewHolder extends RecyclerView.ViewHolder {

        //track view objects
        ImageView ivPosterImage;
        ImageView ivBackgroundImage;
        TextView tvTitle;
        TextView tvOverview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //lookup view objects by id
            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPosterImage);
            ivBackgroundImage = (ImageView) itemView.findViewById(R.id.ivBackdropImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
        }
    }
}

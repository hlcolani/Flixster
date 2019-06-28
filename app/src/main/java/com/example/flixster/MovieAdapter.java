package com.example.flixster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.models.Config;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //track view objects
        @Nullable @BindView(R.id.ivPosterImage) ImageView ivPosterImage;
        @Nullable @BindView(R.id.ivBackdropImage) ImageView ivBackgroundImage;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }
        }
    }
}



package com.example.moviewatcher;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviewatcher.adapter.MovieAdapter;
import com.example.moviewatcher.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView moviesRecyclerView;
    private MovieAdapter movieAdapter;

    private List<MovieModel> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        movieList = new ArrayList<>();

        moviesRecyclerView = findViewById(R.id.moviesRecyclerView);
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(this);
        moviesRecyclerView.setAdapter(movieAdapter);

        MovieModel movie = new MovieModel();
        movie.setId(1);
        movie.setStatus(0);

        movie.setName("Test movie name!");

        movieList.add(movie);
        movieList.add(movie);
        movieList.add(movie);
        movieList.add(movie);
        movieList.add(movie);

        movieAdapter.setMovies(movieList);
    }

}

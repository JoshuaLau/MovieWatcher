package com.example.moviewatcher;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviewatcher.adapter.MovieAdapter;
import com.example.moviewatcher.model.MovieModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView moviesRecyclerView;
    private MovieAdapter movieAdapter;
    private FloatingActionButton fab;

    private List<MovieModel> movieList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        fab = findViewById(R.id.fab);

        getMovies();
        movieAdapter.setMovies(movieList);

        fab.setOnClickListener(v -> AddNewMovie.newInstance().show(getSupportFragmentManager(), AddNewMovie.TAG));
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
    }

    public void getMovies() {
        movieList.clear();
        db.collection("moviedb").limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                for (int i = 0; i < docs.size(); i++) {
                    DocumentSnapshot doc = docs.get(i);
                    String curr = doc.get("title").toString();
                    MovieModel currModel = new MovieModel();
                    currModel.setName(curr);
                    movieList.add(currModel);
                }
            }
        });
    }
}

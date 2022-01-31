package com.example.moviewatcher.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.example.moviewatcher.MainActivity;
import com.example.moviewatcher.R;
import com.example.moviewatcher.model.MovieModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<MovieModel> movieList;
    private MainActivity activity;
    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("moviedb/titles");

    public MovieAdapter(MainActivity activity) {
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieModel item = movieList.get(position);
        holder.movie.setText(item.getName());
        holder.movie.setChecked(toBoolean(item.getStatus()));
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    public int getItemCount() {
        return movieList.size();
    }

    public void setMovies(List<MovieModel> movies) {
        this.movieList = movies;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox movie;

        ViewHolder(View view) {
            super(view);
            movie = view.findViewById(R.id.movieCheckBox);
        }
    }

}

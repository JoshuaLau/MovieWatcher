package com.example.moviewatcher;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.moviewatcher.model.MovieModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNewMovie extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";

    private EditText newMovieText;
    private Button newMovieSaveButton;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static AddNewMovie newInstance() {
        return new AddNewMovie();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newMovieText = getView().findViewById(R.id.newMovieText);
        newMovieSaveButton = getView().findViewById(R.id.newMovieButton);

        final Bundle bundle = getArguments();

        boolean isUpdate = true;

        Log.d("HHHH", "here1");

        if (bundle != null) {
            isUpdate = true;
            Log.d("HHHH", "here2");
            String movie = bundle.getString("movie");
            newMovieText.setText(movie);

            if (movie.length() > 0) {
                newMovieSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_200));
            }
        }

        Log.d("HHHH", "here3");

        newMovieText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    newMovieSaveButton.setEnabled(false);
                    newMovieSaveButton.setTextColor(Color.GRAY);
                } else {
                    newMovieSaveButton.setEnabled(true);
                    newMovieSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_200));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Log.d("HHHH", "here4");

        boolean finalIsUpdate = isUpdate;
        newMovieSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newMovieText.getText().toString();
                Log.d("HHHH", "here5 " + text);
                if (finalIsUpdate) {
                    Map<String, Object> dataToSave = new HashMap<>();
                    dataToSave.put("title", text);
                    db.collection("moviedb").add(dataToSave).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Log.d("MovieTitle", "Movie title has been saved!");
                            } else {
                                Log.w("MovieTitle", "Not saved,", task.getException());
                            }
                        }
                    });
                } else {
                    MovieModel movieModel = new MovieModel();
                    movieModel.setName(text);
                    movieModel.setStatus(0);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }
    }
}

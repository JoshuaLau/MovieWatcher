import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.moviewatcher.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewMovie extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";

    private EditText newMovieText;
    private Button newMovieSaveButton;

    public static AddNewMovie newInstance() {
        return new AddNewMovie();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }
}

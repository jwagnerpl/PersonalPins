package teamtreehouse.com.youtube_learning_buddy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;


public class CommentFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Comments")
                .setPositiveButton("OK", null)
                .create();
    }
}

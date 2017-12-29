package teamtreehouse.com.youtube_learning_buddy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils extends AppCompatActivity {

    public void hideKeyboard(Context context, View view){

        InputMethodManager inputManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}

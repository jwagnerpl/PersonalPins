package teamtreehouse.com.personal_pins.Utilities;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import teamtreehouse.com.personal_pins.Database.AppDatabase;

public class Utils extends AppCompatActivity {

    public void hideKeyboard(Context context, View view){

        InputMethodManager inputManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public AppDatabase createDatabase(Context context) {
        final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "production")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        return db;
    }

    public static int calculateNoOfColumns(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 150);
        return noOfColumns;
    }

    public static float setGridMargins(Context context, float columns){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        Log.d("utils.java", dpWidth + "dpwidth");
        float total = 160 * columns;
        float remainingSpace = (dpWidth - total);
        Log.d("utils.java", remainingSpace + "remainingspace");
        return remainingSpace;
    }

}

package teamtreehouse.com.youtube_learning_buddy.UI;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import teamtreehouse.com.youtube_learning_buddy.Database.AppDatabase;
import teamtreehouse.com.youtube_learning_buddy.Model.Category;
import teamtreehouse.com.youtube_learning_buddy.R;

public class CreateCategoryActivity extends AppCompatActivity {

    private static final String TAG = "CreateCategoryActivity";
    EditText categoryName;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);

        categoryName = findViewById(R.id.category_name);
        button = findViewById(R.id.button);


        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries().
                        fallbackToDestructiveMigration()
                .build();

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                view.animate()
                        .scaleX(0).scaleY(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                int dbSize = db.categoryDao().getAllCategories().size();
                                Log.d(TAG, String.valueOf(dbSize));
                                Category category = new Category(categoryName.getText().toString(), dbSize + 1);
                                db.categoryDao().insertAll(category);
                                startActivity(new Intent(CreateCategoryActivity.this, MainActivity.class));
                            }
                        })
                        .start();


            }
        });
    }
}

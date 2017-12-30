package teamtreehouse.com.youtube_learning_buddy.UI;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import teamtreehouse.com.youtube_learning_buddy.Adapter.CategoryAdapter;
import teamtreehouse.com.youtube_learning_buddy.Database.AppDatabase;
import teamtreehouse.com.youtube_learning_buddy.ItemTouchHelper.ItemTouchHelperAdapter;
import teamtreehouse.com.youtube_learning_buddy.ItemTouchHelper.ItemTouchHelperCallback;
import teamtreehouse.com.youtube_learning_buddy.Model.Category;
import teamtreehouse.com.youtube_learning_buddy.R;
import teamtreehouse.com.youtube_learning_buddy.API.YoutubeApiCall;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    RecyclerView recyclerView;
    private static final String TAG = "MainActivity";
    FloatingActionButton fab;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        YoutubeApiCall api = new YoutubeApiCall();


        context = getApplicationContext();
        recyclerView = findViewById(R.id.recyclerView);


        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        List<Category> categories = db.categoryDao().getAllCategories();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CategoryAdapter(categories, MainActivity.this);
        ItemTouchHelperAdapter adapter;
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback((ItemTouchHelperAdapter) mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter);

        fab = findViewById(R.id.fab);

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(MainActivity.this, "Add a category", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateCategoryActivity.class);
                startActivity(intent);

            }
        });

    }

}

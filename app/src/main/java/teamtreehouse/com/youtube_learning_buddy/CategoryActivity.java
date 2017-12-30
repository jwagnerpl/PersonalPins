package teamtreehouse.com.youtube_learning_buddy;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    public static Context context;
    ImageButton deleteButton;
    RecyclerView recyclerView;
    private static final String TAG = "MainActivity";
    FloatingActionButton fab;
    private RecyclerView.Adapter mAdapter;
    private String fk;
    String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        AppDatabase db = new Utils().createDatabase(CategoryActivity.this);
        final int fk = db.categoryDao().getCategoryId(categoryName);
        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        YoutubeApiCall api = new YoutubeApiCall();

        context = getApplicationContext();
        recyclerView = findViewById(R.id.categoryRecycler);

        List<YoutubeVideo> videos = db.youtubeVideoDao().getAllMovies(fk + "");
        Log.d(TAG, videos.toString());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CategoryVideoAdapter mAdapter = new CategoryVideoAdapter(videos, CategoryActivity.this);
        mAdapter.setVideos(videos);

////        ItemTouchHelperAdapter adapter;
////        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback((ItemTouchHelperAdapter) mAdapter);
//        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter);

        fab = findViewById(R.id.categoryFab);

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(CategoryActivity.this, "Add a Youtube video", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, SearchVideosActivity.class);
                intent.putExtra("CATEGORY_ID", fk+"");
                intent.putExtra("CATEGORY_NAME", categoryName);
                startActivity(intent);

            }
        });

    }
}


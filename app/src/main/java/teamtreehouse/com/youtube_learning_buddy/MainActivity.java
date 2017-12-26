package teamtreehouse.com.youtube_learning_buddy;

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
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import teamtreehouse.com.youtube_learning_buddy.ItemTouchHelper.ItemTouchHelperAdapter;
import teamtreehouse.com.youtube_learning_buddy.ItemTouchHelper.ItemTouchHelperCallback;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    ImageButton deleteButton;
    RecyclerView recyclerView;
    private static final String TAG = "MainActivity";
    FloatingActionButton fab;
    private RecyclerView.Adapter mAdapter;
//    ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(YoutubeClient.YOUTUBE_BASE_URL)
                .addConverterFactory(GsonConverterFactory
                        .create());

        Retrofit retrofit = builder.build();

        YoutubeClient ytc = retrofit.create(YoutubeClient.class);
        Call<YoutubeVideoData> call = ytc.searchedVideos("surfing");

        call.enqueue(new Callback<YoutubeVideoData>() {
            @Override
            public void onResponse(Call<YoutubeVideoData> call, Response<YoutubeVideoData> response) {
                Log.d(TAG, response.toString());
            }

            @Override
            public void onFailure(Call<YoutubeVideoData> call, Throwable t) {
                Log.d(TAG, "something went wrong " + t );
            }
        });

        context = getApplicationContext();
        recyclerView = findViewById(R.id.recyclerView);


        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        List<Category> categories = db.categoryDao().getAllCategories();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CategoryAdapter(categories);
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
                view.setScaleX(2);
                view.setScaleY(2);
                view.animate()
                        .scaleX(0).scaleY(0).setDuration(750)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(MainActivity.this, CreateCategory.class));
                            }
                        })
                        .start();

            }
        });

    }
}

package teamtreehouse.com.to_dolist;

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

import java.util.List;

import teamtreehouse.com.to_dolist.ItemTouchHelper.ItemTouchHelperAdapter;
import teamtreehouse.com.to_dolist.ItemTouchHelper.ItemTouchHelperCallback;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    ImageButton deleteButton;
    RecyclerView recyclerView;
    private static final String TAG = "MainActivity";
    FloatingActionButton fab;
    private RecyclerView.Adapter mAdapter;
//    ArrayList<Task> tasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = getApplicationContext();
        recyclerView = findViewById(R.id.recyclerView);


        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        List<Task> tasks = db.taskDao().getAllTasks();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TaskAdapter(tasks);
        ItemTouchHelperAdapter adapter;
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback((ItemTouchHelperAdapter) mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter);

        fab = findViewById(R.id.fab);
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
                                startActivity(new Intent(MainActivity.this, CreateTask.class));
                            }
                        })
                        .start();

            }
        });

    }

}

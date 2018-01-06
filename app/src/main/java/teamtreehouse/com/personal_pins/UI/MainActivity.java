package teamtreehouse.com.personal_pins.UI;

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

import teamtreehouse.com.personal_pins.Adapter.CategoryAdapter;
import teamtreehouse.com.personal_pins.Database.AppDatabase;
import teamtreehouse.com.personal_pins.Model.Category;
import teamtreehouse.com.personal_pins.R;
import teamtreehouse.com.personal_pins.Utilities.ItemTouchHelper.ItemTouchHelperAdapter;
import teamtreehouse.com.personal_pins.Utilities.ItemTouchHelper.ItemTouchHelperCallback;

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
        getSupportActionBar().setTitle("PinzRfuN");
        getSupportActionBar().setSubtitle("Boards");



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
                Toast.makeText(MainActivity.this, "Add a Board", Toast.LENGTH_LONG).show();
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

package teamtreehouse.com.youtube_learning_buddy;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchVideosActivity extends AppCompatActivity {

    Button searchSubmitButton;
    EditText searchQuery;
    String queryText;
    RecyclerView recyclerView;
    ArrayList<YoutubeVideo> videos;
    private static final String TAG = "SearchVideosActivity";
    public static ProgressBar progressBar;
    Utils utils = new Utils();
    public static FragmentManager manager;
    public static CommentFragment commentFragment;
    public static YoutubeVideo selectedVideo = null;
    public static String categoryId;
    public static String categoryName;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_movie_menu, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(selectedVideo == null){
            Toast.makeText(SearchVideosActivity.this,"Sorry, please select a video", Toast.LENGTH_LONG).show();
        }
        else{
            AppDatabase db = new Utils().createDatabase(SearchVideosActivity.this);
            selectedVideo.setCategoryFk(categoryId);
            db.youtubeVideoDao().insertAll(selectedVideo);
            Log.d(TAG, selectedVideo.toString());
            Intent intent = new Intent(SearchVideosActivity.this, CategoryActivity.class);
            intent.putExtra("CATEGORY_NAME", categoryName);
            startActivity(intent);
        }

            return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_videos);
        progressBar = findViewById(R.id.searchProgressBar);
        categoryName = getIntent().getStringExtra("CATEGORY_NAME");
        categoryId = getIntent().getStringExtra("CATEGORY_ID");

        manager = getFragmentManager();
        commentFragment = new CommentFragment();


        searchSubmitButton = findViewById(R.id.searchButton);
        searchQuery = findViewById(R.id.searchInput);
        recyclerView = findViewById(R.id.searchResultsRecyclerView);
        final Context context = SearchVideosActivity.this;

        //new YoutubeApiCall().getComments(SearchVideosActivity.this, "puyiZKvxBa0", progressBar, commentFragment, manager);

        searchSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryText = searchQuery.getText().toString();
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchVideosActivity.this));
                videos = new YoutubeApiCall().youtubeSearch(context, queryText, recyclerView, progressBar);
                utils.hideKeyboard(SearchVideosActivity.this, view);
            }
        });
    }

}

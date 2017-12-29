package teamtreehouse.com.youtube_learning_buddy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class SearchVideos extends AppCompatActivity {

    Button searchSubmitButton;
    EditText searchQuery;
    String queryText;
    RecyclerView recyclerView;
    ArrayList<YoutubeVideo> videos;
    private static final String TAG = "SearchVideos";
    ProgressBar progressBar;
    Utils utils = new Utils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_videos);
        progressBar = findViewById(R.id.searchProgressBar);

        searchSubmitButton = findViewById(R.id.searchButton);
        searchQuery = findViewById(R.id.searchInput);
        recyclerView = findViewById(R.id.searchResultsRecyclerView);
        final Context context = SearchVideos.this;

        searchSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryText = searchQuery.getText().toString();
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchVideos.this));
                videos = new YoutubeApiCall().youtubeSearch(context, queryText, recyclerView, progressBar);
                utils.hideKeyboard(SearchVideos.this, view);
            }
        });
    }

}

package teamtreehouse.com.youtube_learning_buddy;

import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YoutubeApiCall {
    private static final String TAG = "YoutubeApiCall";

    public ArrayList<YoutubeVideo> youtubeSearch(String query) {

        final ArrayList<YoutubeVideo> videoList = new ArrayList<>();


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(YoutubeClient.YOUTUBE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        YoutubeClient ytc = retrofit.create(YoutubeClient.class);
        Call<YoutubeVideoData> call = ytc.searchedVideos(query);

        call.enqueue(new Callback<YoutubeVideoData>() {
            @Override
            public void onResponse(Call<YoutubeVideoData> call, Response<YoutubeVideoData> response) {

                YoutubeVideoData ytv = response.body();

                ArrayList<Item> items = ytv.getItems();
                for (Item item : items) {
                    String description = item.getSnippet().getDescription();
                    String title = item.getSnippet().getTitle();
                    String url = "https://youtube.com/watch?v=" + item.getId().getVideoId();
                    videoList.add(new YoutubeVideo(url,title,description));
                    Log.d(TAG, description + " " + url + " " + title);
                }
            }

            @Override
            public void onFailure(Call<YoutubeVideoData> call, Throwable t) {
                Log.d(TAG, "something went wrong " + t);
            }
        });
        return videoList;
    }
}

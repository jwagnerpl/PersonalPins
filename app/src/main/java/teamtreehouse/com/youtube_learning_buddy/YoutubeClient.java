package teamtreehouse.com.youtube_learning_buddy;

import android.media.Image;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface YoutubeClient {
        String YOUTUBE_BASE_URL = "https://www.googleapis.com/youtube/v3/";

        @GET("search?part=snippet&type=title&video&maxResults=25&Caption=closedCaption&key=AIzaSyCr5GTbQfhuCIytM0irQjEDYqBWWMwm1rg&part=snippet")
        Call<YoutubeVideoData> searchedVideos(@Query("q") String query);
    }

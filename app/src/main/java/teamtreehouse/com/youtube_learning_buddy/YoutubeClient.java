package teamtreehouse.com.youtube_learning_buddy;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface YoutubeClient {
        String YOUTUBE_BASE_URL = "https://www.googleapis.com/youtube/v3/";

        @GET("search?part=snippet&type=title&video&maxResults=25&Caption=closedCaption&key=AIzaSyCr5GTbQfhuCIytM0irQjEDYqBWWMwm1rg&part=snippet")
        Observable<YoutubeVideoData> searchedVideos(@Query("q") String query);
    }

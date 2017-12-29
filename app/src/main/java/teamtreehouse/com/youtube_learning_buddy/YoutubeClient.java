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
        String YOUTUBE_COMMENT_URL = "https://www.googleapis.com/youtube/v3/commentThreads/";

        @GET("search?part=snippet&type=title&video&maxResults=25&Caption=closedCaption&key=AIzaSyCr5GTbQfhuCIytM0irQjEDYqBWWMwm1rg&part=snippet")
        Observable<YoutubeVideoData> searchedVideos(@Query("q") String query);

        @GET("?part=snippet&maxResults=20&order=relevance&key=AIzaSyCr5GTbQfhuCIytM0irQjEDYqBWWMwm1rg")
        Observable<YoutubeCommentData> returnComments(@Query("videoId") String videoId);
    }

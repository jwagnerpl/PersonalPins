package teamtreehouse.com.youtube_learning_buddy.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class YoutubeUrl {

    @SerializedName("url")
    String url;

    public String getUrl() {
        return url;
    }
}

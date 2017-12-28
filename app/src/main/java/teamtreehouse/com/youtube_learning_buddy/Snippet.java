package teamtreehouse.com.youtube_learning_buddy;

import com.google.gson.annotations.SerializedName;

public class Snippet {

    @SerializedName("title")
    String title;

    @SerializedName("default")
    YoutubeUrl url;

    @SerializedName("description")
    String description;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
package teamtreehouse.com.youtube_learning_buddy;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class YoutubeVideoData {

    @SerializedName("kind")
    String kind;

    @SerializedName("items")
    ArrayList<Item> items;

    public String getKind() {
        return kind;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}

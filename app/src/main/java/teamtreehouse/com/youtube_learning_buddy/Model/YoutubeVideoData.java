package teamtreehouse.com.youtube_learning_buddy.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import teamtreehouse.com.youtube_learning_buddy.Model.Item;

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

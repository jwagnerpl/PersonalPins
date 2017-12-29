package teamtreehouse.com.youtube_learning_buddy;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class YoutubeCommentData {

    @SerializedName("kind")
    String kind;

    @SerializedName("items")
    ArrayList<CommentItems> commentItems;

    public ArrayList<CommentItems> getCommentItems() {
        return commentItems;
    }

    public String getKind() {
        return kind;
    }
}

package teamtreehouse.com.youtube_learning_buddy.Model;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("snippet")
    Snippet snippet;

    @SerializedName("id")
    Id id;

    public Snippet getSnippet() {
        return snippet;
    }

    public Id getId() {
        return id;
    }
}

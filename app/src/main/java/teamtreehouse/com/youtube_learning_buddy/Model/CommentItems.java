package teamtreehouse.com.youtube_learning_buddy.Model;

import com.google.gson.annotations.SerializedName;

public class CommentItems {

    @SerializedName("snippet")
    CommentSnippet commentSnippet;

    public CommentSnippet getCommentSnippet() {
        return commentSnippet;
    }
}

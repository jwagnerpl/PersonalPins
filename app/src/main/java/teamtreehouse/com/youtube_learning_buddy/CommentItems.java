package teamtreehouse.com.youtube_learning_buddy;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

class CommentItems {

    @SerializedName("snippet")
    CommentSnippet commentSnippet;

    public CommentSnippet getCommentSnippet() {
        return commentSnippet;
    }
}

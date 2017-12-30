package teamtreehouse.com.youtube_learning_buddy.Model;

import com.google.gson.annotations.SerializedName;

public class TopLevelComment {

    @SerializedName("snippet")
    TopLevelCommentSnippet topLevelCommentSnippet;

    public TopLevelCommentSnippet getTopLevelCommentSnippet() {
        return topLevelCommentSnippet;
    }
}

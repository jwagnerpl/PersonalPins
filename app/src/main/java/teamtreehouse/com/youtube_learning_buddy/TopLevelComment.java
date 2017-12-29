package teamtreehouse.com.youtube_learning_buddy;

import com.google.gson.annotations.SerializedName;

class TopLevelComment {

    @SerializedName("snippet")
    TopLevelCommentSnippet topLevelCommentSnippet;

    public TopLevelCommentSnippet getTopLevelCommentSnippet() {
        return topLevelCommentSnippet;
    }
}

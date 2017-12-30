package teamtreehouse.com.youtube_learning_buddy.Model;

import com.google.gson.annotations.SerializedName;

public class TopLevelCommentSnippet {

    @SerializedName("authorDisplayName")
    public String authorDisplayName;

    @SerializedName("textOriginal")
    public String textOriginal;

    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    public String getTextOriginal() {
        return textOriginal;
    }

    public TopLevelCommentSnippet(String authorDisplayName, String textOriginal) {
        this.authorDisplayName = authorDisplayName;
        this.textOriginal = textOriginal;
    }

}

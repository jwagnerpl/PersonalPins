package teamtreehouse.com.youtube_learning_buddy;

import com.google.gson.annotations.SerializedName;

class TopLevelCommentSnippet {

    @SerializedName("authorDisplayName")
    String authorDisplayName;

    @SerializedName("textOriginal")
    String textOriginal;

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

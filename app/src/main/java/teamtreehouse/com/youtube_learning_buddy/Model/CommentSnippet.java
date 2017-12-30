package teamtreehouse.com.youtube_learning_buddy.Model;

import com.google.gson.annotations.SerializedName;

public class CommentSnippet {

    @SerializedName("topLevelComment")
    public TopLevelComment topLevelComment;

    public TopLevelComment getTopLevelComment() {
        return topLevelComment;
    }
}

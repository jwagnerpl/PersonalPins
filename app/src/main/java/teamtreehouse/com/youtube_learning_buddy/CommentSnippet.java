package teamtreehouse.com.youtube_learning_buddy;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

class CommentSnippet {

    @SerializedName("topLevelComment")
    TopLevelComment topLevelComment;

    public TopLevelComment getTopLevelComment() {
        return topLevelComment;
    }
}

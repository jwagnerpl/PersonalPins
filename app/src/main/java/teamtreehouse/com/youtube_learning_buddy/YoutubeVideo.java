package teamtreehouse.com.youtube_learning_buddy;

import org.json.JSONObject;

public class YoutubeVideo {

    public YoutubeVideo(String videoTitle) {
        this.title = videoTitle;
    }

    String title;

    String description;
    JSONObject youtubeJson;

    public YoutubeVideo() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JSONObject getYoutubeJson() {
        return youtubeJson;
    }

    public void setYoutubeJson(JSONObject youtubeJson) {
        this.youtubeJson = youtubeJson;
    }
}

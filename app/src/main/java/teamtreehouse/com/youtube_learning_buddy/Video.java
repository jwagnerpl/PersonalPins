package teamtreehouse.com.youtube_learning_buddy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Video")
public class Video {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "videoName")
    private String videoName;

    @ColumnInfo(name = "videoOrder")
    private int videoOrder;

    @ColumnInfo(name = "videoCategory")
    private int videoCategory;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Video(String videoName, int categoryOrder) {
        this.videoName = videoName;
        this.videoOrder = categoryOrder;
    }

    public int getVideoOrder() {
        return videoOrder;
    }

    public void setVideoOrder(int categoryOrder) {
        this.videoOrder = videoOrder;
    }

    public int getVideoCategory() {
        return videoCategory;
    }

    public void setVideoCategory(int videoCategory) {
        this.videoCategory = videoCategory;
    }
}


package teamtreehouse.com.personal_pins.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Photo")
public class Photo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "photoUri")
    private String photoUri;

    @ColumnInfo(name = "fkBoardId")
    private int fkBoardId;

    @ColumnInfo(name = "tags")
    private String tags;

    @ColumnInfo(name = "comments")
    private String comments;

    @ColumnInfo(name = "title")
    private String title;

    public Photo(String photoUri, int fkBoardId, String tags, String comments, String title) {
        this.photoUri = photoUri;
        this.fkBoardId = fkBoardId;
        this.tags = tags;
        this.comments = comments;
        this.title = title;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public int getFkBoardId() {
        return fkBoardId;
    }

    public void setFkBoardId(int fkBoardId) {
        this.fkBoardId = fkBoardId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}


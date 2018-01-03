package teamtreehouse.com.youtube_learning_buddy.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import teamtreehouse.com.youtube_learning_buddy.Model.Category;
import teamtreehouse.com.youtube_learning_buddy.Model.Photo;
import teamtreehouse.com.youtube_learning_buddy.Model.YoutubeVideo;

@Database(entities = {Category.class, YoutubeVideo.class, Photo.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract YoutubeVideoDao youtubeVideoDao();
    public abstract PhotoDao photoDao();
}

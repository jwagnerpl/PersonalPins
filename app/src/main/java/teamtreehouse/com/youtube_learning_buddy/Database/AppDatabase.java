package teamtreehouse.com.youtube_learning_buddy.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import teamtreehouse.com.youtube_learning_buddy.Model.Category;
import teamtreehouse.com.youtube_learning_buddy.Model.YoutubeVideo;

@Database(entities = {Category.class, YoutubeVideo.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract YoutubeVideoDao youtubeVideoDao();
}

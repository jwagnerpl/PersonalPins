package teamtreehouse.com.youtube_learning_buddy;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Category.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract VideoDao videoDao();
}

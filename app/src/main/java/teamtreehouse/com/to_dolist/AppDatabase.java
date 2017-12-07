package teamtreehouse.com.to_dolist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Task.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}

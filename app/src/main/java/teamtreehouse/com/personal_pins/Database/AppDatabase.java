package teamtreehouse.com.personal_pins.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import teamtreehouse.com.personal_pins.Model.Category;
import teamtreehouse.com.personal_pins.Model.Photo;

@Database(entities = {Category.class, Photo.class}, version = 10)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract PhotoDao photoDao();
}

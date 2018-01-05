package teamtreehouse.com.youtube_learning_buddy.Database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import teamtreehouse.com.youtube_learning_buddy.Model.Photo;

@Dao
public interface PhotoDao {

    @Query("SELECT * FROM Photo Where fkBoardId LIKE :fk")
    List<Photo> getAllPhotosFromBoard(String fk);

    @Query("SELECT * FROM Photo WHERE id LIKE :id ")
    Photo getPhoto(int id);

    @Insert
    void insertAll(Photo... photos);

    @Delete
    void delete(Photo... photos);

    @Update
    void update(Photo... photos);
}

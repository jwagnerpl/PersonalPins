package teamtreehouse.com.youtube_learning_buddy;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface VideoDao {

    @Query("SELECT * FROM Video ORDER BY categoryOrder ASC")
    List<Category> getAllVideos();

    @Query("SELECT * FROM Video WHERE id LIKE :id ")
    Category getVideo(int id);

    @Insert
    void insertAll(Video... videos);

    @Insert
    void insertVideoList(ArrayList<Video> videoLists);

    @Update
    void updateVideoOrder(Video... videoOrder);

    @Delete
    void delete(Video... videos);

}

package teamtreehouse.com.youtube_learning_buddy.Database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import teamtreehouse.com.youtube_learning_buddy.Model.YoutubeVideo;

@Dao
public interface YoutubeVideoDao {

    @Query("SELECT * FROM YoutubeVideo Where categoryFk LIKE :fk")
    List<YoutubeVideo> getAllMovies(String fk);

    @Query("SELECT * FROM YoutubeVideo WHERE id LIKE :id ")
    YoutubeVideo getVideo(int id);

    @Insert
    void insertAll(YoutubeVideo... youtubeVideos);

    @Update
    void updateVideoOrder(YoutubeVideo... videoOrder);

    @Delete
    void delete(YoutubeVideo... youtubeVideos);

}

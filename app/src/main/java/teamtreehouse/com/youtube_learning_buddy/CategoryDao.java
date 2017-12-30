package teamtreehouse.com.youtube_learning_buddy;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM Category ORDER BY categoryOrder ASC")
    List<Category> getAllCategories();

    @Query("SELECT * FROM Category WHERE id LIKE :id ")
    Category getCategory(int id);

    @Query("SELECT * FROM Category WHERE categoryName LIKE :categoryName ")
    int getCategoryId(String categoryName);

    @Insert
    void insertAll(Category... categories);

    @Insert
    void insertCategoryList(ArrayList<Category> categoryLists);

    @Update
    void updateCategoryOrder(Category... categoryOrder);

    @Delete
    void delete(Category... categories);

}

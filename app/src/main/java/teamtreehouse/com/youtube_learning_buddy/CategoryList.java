package teamtreehouse.com.youtube_learning_buddy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "CategoryList")
public class CategoryList {

    @PrimaryKey(autoGenerate = true)
    private int id = 1;

    @ColumnInfo(name = "categoryList")
    private ArrayList<Category> categoryList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }

}

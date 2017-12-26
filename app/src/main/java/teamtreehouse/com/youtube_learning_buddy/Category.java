package teamtreehouse.com.youtube_learning_buddy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Category")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "categoryName")
    private String categoryName;

    @ColumnInfo(name = "categoryOrder")
    private int categoryOrder;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category(String categoryName, int categoryOrder) {
        this.categoryName = categoryName;
        this.categoryOrder = categoryOrder;
    }

    public int getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(int categoryOrder) {
        this.categoryOrder = categoryOrder;
    }
}


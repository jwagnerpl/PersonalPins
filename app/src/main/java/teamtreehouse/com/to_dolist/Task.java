package teamtreehouse.com.to_dolist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Task")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "taskName")
    private String taskName;

    @ColumnInfo(name = "taskOrder")
    private int taskOrder;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Task(String taskName, int taskOrder) {
        this.taskName = taskName;
        this.taskOrder = taskOrder;
    }

    public int getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(int taskOrder) {
        this.taskOrder = taskOrder;
    }
}


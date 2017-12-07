package teamtreehouse.com.to_dolist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "TaskList")
public class TaskList {

    @PrimaryKey(autoGenerate = true)
    private int id = 1;

    @ColumnInfo(name = "taskList")
    private ArrayList<Task> taskList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

}

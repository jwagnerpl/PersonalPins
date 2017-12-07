package teamtreehouse.com.to_dolist;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task ORDER BY taskOrder ASC")
    List<Task> getAllTasks();

    @Query("SELECT * FROM task WHERE id LIKE :id ")
    Task getTask(int id);

    @Insert
    void insertAll(Task... tasks);

    @Insert
    void insertTaskList(ArrayList<Task> taskLists);

    @Update
    void updateTaskOrder(Task... taskOrder);

    @Delete
    void delete(Task... tasks);

}

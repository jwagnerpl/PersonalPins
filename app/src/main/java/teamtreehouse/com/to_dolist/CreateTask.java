package teamtreehouse.com.to_dolist;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class CreateTask extends AppCompatActivity {

    private static final String TAG = "CreateTask";
    EditText taskName;
    private Button button;
    public ArrayList<Task> taskList1 = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        taskName = findViewById(R.id.task_name);
        button = findViewById(R.id.button);


        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries().
                        fallbackToDestructiveMigration()
                .build();

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                view.animate()
                        .scaleX(0).scaleY(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                int dbSize = db.taskDao().getAllTasks().size();
                                Log.d(TAG, String.valueOf(dbSize));
                                Task task = new Task(taskName.getText().toString(), dbSize + 1);
                                db.taskDao().insertAll(task);
                                startActivity(new Intent(CreateTask.this, MainActivity.class));
                            }
                        })
                        .start();


            }
        });
    }
}

package teamtreehouse.com.to_dolist;

import android.animation.ObjectAnimator;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import teamtreehouse.com.to_dolist.ItemTouchHelper.ItemTouchHelperAdapter;

class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> implements ItemTouchHelperAdapter, View.OnLongClickListener {

    List<Task> tasks;

    private static final String TAG = "TaskAdapter";
    final AppDatabase db = Room.databaseBuilder(MainActivity.context, AppDatabase.class, "production")
            .allowMainThreadQueries().fallbackToDestructiveMigration()
            .build();

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(tasks, i, i + 1);
                tasks.get(fromPosition).setTaskOrder(toPosition);
               /* db.taskDao().updateTaskOrder(tasks.get(fromPosition));
                tasks = db.taskDao().getAllTasks();
                Log.d(TAG, "first method - i is " + i);*/
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(tasks, i, i - 1);
                /*tasks.get(fromPosition).setTaskOrder(fromPosition - 1);
                db.taskDao().updateTaskOrder(tasks.get(fromPosition));
                Log.d(TAG, "second i is " + i);*/
            }


        }

        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setTaskOrder(i);
            db.taskDao().updateTaskOrder(tasks.get(i));
            Log.d(TAG, tasks.get(i) + " is set as the " + i + " item");
        }


        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {

    }


    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder, int position) {
        holder.taskName.setText(tasks.get(position).getTaskName());
        Log.d(TAG, tasks.get(position).getTaskOrder() + "");
        int taskId = tasks.get(position).getId();
        holder.imageButton.setTag(R.string.task_id, taskId);
        holder.imageButton.setTag(R.string.position, position);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        public TextView taskName;
        public ImageButton imageButton;
        public TextView taskId;

        public ViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name);
            taskId = itemView.findViewById(R.id.task_id);
            imageButton = itemView.findViewById(R.id.delete);
            imageButton.setOnClickListener(this);
            taskName.setOnClickListener(this);
        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {

            if (view == imageButton) {

                int taskId = (int) view.getTag(R.string.task_id);
                final int taskPosition = (int) view.getTag(R.string.position);
                Log.d(TAG, taskPosition + "");

                Task task = db.taskDao().getTask(taskId);
                db.taskDao().delete(task);
                tasks.remove(taskPosition);

                view.animate()
                        .scaleX(0).scaleY(0).setDuration(700)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                notifyDataSetChanged();
                            }
                        })
                        .start();

            }
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}

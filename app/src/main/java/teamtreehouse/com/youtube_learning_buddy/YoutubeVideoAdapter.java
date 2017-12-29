package teamtreehouse.com.youtube_learning_buddy;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import teamtreehouse.com.youtube_learning_buddy.ItemTouchHelper.ItemTouchHelperAdapter;

class YoutubeVideoAdapter extends RecyclerView.Adapter<YoutubeVideoAdapter.ViewHolder> /*implements ItemTouchHelperAdapter, View.OnLongClickListener*/ {

    List<YoutubeVideo> videos;
    LinearLayout row;
    Context context;
    MyClickListenerInterface myClickListenerInterface;
    private int selectedPos = -10;
    private int row_index = -1;


    private static final String TAG = "CategoryAdapter";
    final AppDatabase db = Room.databaseBuilder(MainActivity.context, AppDatabase.class, "production")
            .allowMainThreadQueries().fallbackToDestructiveMigration()
            .build();


    public YoutubeVideoAdapter(List<YoutubeVideo> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    public List<YoutubeVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<YoutubeVideo> videos) {
        this.videos = videos;
    }

    //declare interface
    private OnItemClicked onClick;

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }



    @Override
    public YoutubeVideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final YoutubeVideoAdapter.ViewHolder holder, final int position) {
//        holder.categoryName.setText(categories.get(position).getCategoryName());
//        Log.d(TAG, categories.get(position).getCategoryOrder() + "");
//        int categoryId = categories.get(position).getId();
//        holder.imageButton.setTag(R.string.category_id, categoryId);
//        holder.imageButton.setTag(R.string.position, position);
        int selectedColor = context.getResources().getColor(R.color.selected);
        holder.itemView.setSelected(selectedPos == position);
        holder.videoName.setText(videos.get(position).getTitle());
        holder.videoDescription.setText(videos.get(position).getDescription());
        holder.videoRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();
            }
        });

        if(row_index == position){
            Log.d(TAG, row_index + "");
            Log.d(TAG, position + "");
            holder.videoRow.setBackgroundColor(selectedColor);
        }
        else{
            holder.videoRow.setBackgroundColor(Color.parseColor("#ffffff"));
        }

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

//    @Override
//    public boolean onLongClick(View view) {
//        return false;
//    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        public TextView videoName;
        public TextView videoDescription;
        public TextView videoUrl;
        public TextView videoId;
        public LinearLayout videoRow;

        public ViewHolder(View itemView) {
            super(itemView);
            row = itemView.findViewById(R.id.videoRow);
            videoName = itemView.findViewById(R.id.video_name);
            videoId = itemView.findViewById(R.id.video_id);
            videoDescription = itemView.findViewById(R.id.video_description);
            videoUrl = itemView.findViewById(R.id.video_url);
            //videoName.setOnClickListener(this);
            videoRow = itemView.findViewById(R.id.videorow2);

        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {

            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);

        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

}

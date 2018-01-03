package teamtreehouse.com.youtube_learning_buddy.Adapter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import teamtreehouse.com.youtube_learning_buddy.Database.AppDatabase;
import teamtreehouse.com.youtube_learning_buddy.Model.Photo;
import teamtreehouse.com.youtube_learning_buddy.UI.MainActivity;
import teamtreehouse.com.youtube_learning_buddy.R;

public class CategoryPhotoAdapter extends RecyclerView.Adapter<CategoryPhotoAdapter.ViewHolder> /*implements ItemTouchHelperAdapter, View.OnLongClickListener*/ {

    List<Photo> photos;
    LinearLayout row;
    Context context;
    private int row_index = -1;
    ProgressBar pb;
    Uri photoUri;

    private static final String TAG = "CategoryAdapter";
    final AppDatabase db = Room.databaseBuilder(MainActivity.context, AppDatabase.class, "production")
            .allowMainThreadQueries().fallbackToDestructiveMigration()
            .build();

    public CategoryPhotoAdapter(List<Photo> photos, Context context) {
        this.photos = photos;
        this.context = context;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public CategoryPhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_category_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryPhotoAdapter.ViewHolder holder, final int position) {

        Log.d(TAG, photos.size() + " this is photo size");

        Log.d(TAG, photos.get(position).getComments() + "comments");
        Log.d(TAG, photos.get(position).getTags() + "tags");
        Log.d(TAG, photos.get(position).getPhotoUri() + "photoUri");

        holder.photoComments.setText(photos.get(position).getComments());
        holder.tags.setText(photos.get(position).getTags());
        photoUri = Uri.parse(photos.get(position).getPhotoUri());
        Picasso.with(context).load(photoUri).into(holder.imageView);

//        int selectedColor = context.getResources().getColor(R.color.selected);
//        holder.tags.setText(photos.get(position).getTitle());
//        holder.photoComments.setText(photos.get(position).getDescription());
//
//
//        holder.photoRow2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                row_index = position;
//                Log.d(TAG, "rowclickreg");
////                notifyDataSetChanged();
//                String url = photos.get(position).getUrl();
//                SearchVideosActivity.selectedVideo = photos.get(position);
//                Uri uri = Uri.parse(url);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                context.startActivity(intent);
//
//            }
//        });
//
//        if (row_index == position) {
//            Log.d(TAG, row_index + "");
//            Log.d(TAG, position + "");
////            holder.photoRow2.setBackgroundColor(Color.parseColor("#aaafff"));
//
//        } else {
////            holder.photoRow2.setBackgroundColor(Color.parseColor("#ffffff"));
//        }

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        public TextView tags;
        public TextView photoComments;
        public LinearLayout photoRow2;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            row = itemView.findViewById(R.id.photoRow);
            tags = itemView.findViewById(R.id.photo_tags);
            photoComments = itemView.findViewById(R.id.photo_comments);
            imageView = itemView.findViewById(R.id.photo_imageView);
        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

}

package teamtreehouse.com.personal_pins.Adapter;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import teamtreehouse.com.personal_pins.Database.AppDatabase;
import teamtreehouse.com.personal_pins.Model.Photo;
import teamtreehouse.com.personal_pins.R;
import teamtreehouse.com.personal_pins.UI.MainActivity;

public class CategoryPhotoAdapter extends RecyclerView.Adapter<CategoryPhotoAdapter.ViewHolder> /*implements ItemTouchHelperAdapter, View.OnLongClickListener*/ {

    List<Photo> photos;
    LinearLayout row;
    Context context;
    Uri photoUri;
    private ItemClickListener itemClickListener;

    private static final String TAG = "CategoryAdapter";
    final AppDatabase db = Room.databaseBuilder(MainActivity.context, AppDatabase.class, "production")
            .allowMainThreadQueries().fallbackToDestructiveMigration()
            .build();
    private Photo currentPhoto;
    private FlexboxLayout tagLayout;

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
        currentPhoto = photos.get(position);
        tagLayout = holder.tagLinearLayout;
        tagLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "tags clicked");
            }
        });
        holder.title.setText(currentPhoto.getTitle());
        holder.photoComments.setText(currentPhoto.getComments());
        photoUri = Uri.parse(currentPhoto.getPhotoUri());
        final FlexboxLayout.LayoutParams lparams = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
        lparams.setMargins(10, 10, 10, 10);
        String tags = currentPhoto.getTags();
        List<String> tagList = Arrays.asList(tags.split(","));
        for (String tag : tagList) {

            TextView tv = new TextView(context);
            tv.setLayoutParams(lparams);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(tag);
            tv.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            this.tagLayout.addView(tv);
        }
        TextView tv = new TextView(context);
        tv.setLayoutParams(lparams);
        tv.setPadding(5, 5, 5, 5);
        tv.setText("+ Add Tag");
        tv.setId(R.id.addTag);
        tv.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        final FlexboxLayout thisTagLayout = this.tagLayout;
        this.tagLayout.addView(tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Add Tag");
                alertDialog.setNegativeButton("Cancel", null);
                final EditText input = new EditText(context);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setPositiveButton("Set Tag", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String tags = photos.get(position).getTags();
                        String inputText = input.getText().toString();
                        String newTags = tags + "," + inputText;
                        photos.get(position).setTags(newTags);
                        Photo updatedPhoto = photos.get(position);
                        db.photoDao().update(updatedPhoto);
                        thisTagLayout.removeAllViews();
                        notifyItemChanged(position);
                    }
                });
                alertDialog.show();
            }
        });

        if (photoUri.toString().contains("jpg") || photoUri.toString().contains("image")) {
            Picasso.with(context).load(photoUri).into(holder.imageView);
            holder.videoView.setVisibility(View.GONE);
        } else if (photoUri.toString().contains("mp4") || photoUri.toString().contains("video")) {
            holder.imageView.setVisibility(View.GONE);
            holder.videoView.setVideoURI(photoUri);
            holder.videoView.seekTo(10);
        }


    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tags;
        public TextView photoComments;
        public ImageView imageView;
        public VideoView videoView;
        public TextView title;
        public FlexboxLayout tagLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            row = itemView.findViewById(R.id.photoRow);
            tags = itemView.findViewById(R.id.photo_tags);
            photoComments = itemView.findViewById(R.id.photo_comments);
            imageView = itemView.findViewById(R.id.photo_imageView);
            videoView = itemView.findViewById(R.id.videoView);
            title = itemView.findViewById(R.id.titleTextView);
            tagLinearLayout = itemView.findViewById(R.id.tagLinearLayout);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public Photo getItem(int id) {
        return photos.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

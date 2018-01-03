package teamtreehouse.com.youtube_learning_buddy.Adapter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import teamtreehouse.com.youtube_learning_buddy.Database.AppDatabase;
import teamtreehouse.com.youtube_learning_buddy.ItemTouchHelper.ItemTouchHelperAdapter;
import teamtreehouse.com.youtube_learning_buddy.UI.MainActivity;
import teamtreehouse.com.youtube_learning_buddy.Model.Category;
import teamtreehouse.com.youtube_learning_buddy.R;
import teamtreehouse.com.youtube_learning_buddy.UI.CategoryActivity;
import teamtreehouse.com.youtube_learning_buddy.Model.YoutubeVideo;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements ItemTouchHelperAdapter, View.OnLongClickListener {

    List<Category> categories;
    List<YoutubeVideo> videos;
    LinearLayout row;
    Context context;
    int id;
    String coverPhotoUri;

    private static final String TAG = "CategoryAdapter";
    final AppDatabase db = Room.databaseBuilder(MainActivity.context, AppDatabase.class, "production")
            .allowMainThreadQueries().fallbackToDestructiveMigration()
            .build();

    public CategoryAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    public CategoryAdapter() {
    }

    public List<YoutubeVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<YoutubeVideo> videos) {
        this.videos = videos;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(categories, i, i + 1);
                categories.get(fromPosition).setCategoryOrder(toPosition);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(categories, i, i - 1);

            }


        }

        for (int i = 0; i < categories.size(); i++) {
            categories.get(i).setCategoryOrder(i);
            db.categoryDao().updateCategoryOrder(categories.get(i));
            Log.d(TAG, categories.get(i) + " is set as the " + i + " item");
        }


        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {

    }


    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        coverPhotoUri = categories.get(position).getCoverPhoto();
        if(coverPhotoUri != null){
            Picasso.with(context).load(Uri.parse(coverPhotoUri)).into(holder.albumCover);
        }
        //holder.
        holder.categoryName.setText(categories.get(position).getCategoryName());
        Log.d(TAG, categories.get(position).getCategoryOrder() + "");
        int categoryId = categories.get(position).getId();
        //holder.imageButton.setTag(R.string.category_id, categoryId);
        //holder.imageButton.setTag(R.string.position, position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        public TextView categoryName;
        public ImageButton imageButton;
        public TextView categoryId;
        public ImageView albumCover;

        public ViewHolder(View itemView) {
            super(itemView);
            row = itemView.findViewById(R.id.row);
            categoryName = itemView.findViewById(R.id.category_name);
            albumCover = itemView.findViewById(R.id.albumCover);

//            imageButton = itemView.findViewById(R.id.delete);
//            imageButton.setOnClickListener(this);
            categoryName.setOnClickListener(this);
        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {

            if (view == categoryName) {
                Log.d(TAG, view.toString());
                Intent intent = new Intent(context, CategoryActivity.class);
                intent.putExtra("CATEGORY_NAME", categoryName.getText().toString());
                context.startActivity(intent);
            }

            if (view == imageButton) {

                int categoryId = (int) view.getTag(R.string.category_id);
                final int categoryPosition = (int) view.getTag(R.string.position);
                Log.d(TAG, categoryPosition + "");

                Category category = db.categoryDao().getCategory(categoryId);
                db.categoryDao().delete(category);
                categories.remove(categoryPosition);

                notifyDataSetChanged();

            }
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}

package teamtreehouse.com.personal_pins.Adapter;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import teamtreehouse.com.personal_pins.Database.AppDatabase;
import teamtreehouse.com.personal_pins.Model.Category;
import teamtreehouse.com.personal_pins.R;
import teamtreehouse.com.personal_pins.UI.CategoryActivity;
import teamtreehouse.com.personal_pins.UI.MainActivity;
import teamtreehouse.com.personal_pins.Utilities.ItemTouchHelper.ItemTouchHelperAdapter;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements ItemTouchHelperAdapter, View.OnLongClickListener {

    List<Category> categories;
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
        if (coverPhotoUri != null) {
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
        public TextView categoryId;
        public ImageView albumCover;

        public ViewHolder(View itemView) {
            super(itemView);
            row = itemView.findViewById(R.id.row);
            categoryName = itemView.findViewById(R.id.category_name);
            albumCover = itemView.findViewById(R.id.albumCover);
            categoryName.setOnClickListener(this);
            albumCover.setOnClickListener(this);
        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {

            if (view == albumCover || view == categoryName) {
                Log.d(TAG, view.toString());
                Intent intent = new Intent(context, CategoryActivity.class);
                intent.putExtra("CATEGORY_NAME", categoryName.getText().toString());
                context.startActivity(intent);
            }

        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}

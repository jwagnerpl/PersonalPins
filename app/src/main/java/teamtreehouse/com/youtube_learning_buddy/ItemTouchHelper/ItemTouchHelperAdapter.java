package teamtreehouse.com.youtube_learning_buddy.ItemTouchHelper;
import android.support.v7.widget.RecyclerView;


public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}

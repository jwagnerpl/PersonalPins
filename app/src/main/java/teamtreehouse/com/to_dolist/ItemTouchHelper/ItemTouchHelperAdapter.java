package teamtreehouse.com.to_dolist.ItemTouchHelper;
import android.support.v7.widget.RecyclerView;


public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}

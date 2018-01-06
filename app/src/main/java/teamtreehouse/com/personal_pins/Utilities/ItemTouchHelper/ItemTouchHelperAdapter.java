package teamtreehouse.com.personal_pins.Utilities.ItemTouchHelper;


public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}

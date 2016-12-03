package calpoly.edu.stracker;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by panktigandhi on 12/2/16.
 */

public class SwipeHelper extends ItemTouchHelper.SimpleCallback {

    TransactionAdapter adapter;
    public SwipeHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public SwipeHelper(TransactionAdapter adapter) {
        super(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.LEFT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.dismissItem(viewHolder.getAdapterPosition());
    }


}

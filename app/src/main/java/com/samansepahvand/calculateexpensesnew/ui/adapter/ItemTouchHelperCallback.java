
package com.samansepahvand.calculateexpensesnew.ui.adapter;

import android.graphics.Canvas;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import static androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags;


public class ItemTouchHelperCallback extends ItemTouchHelperExtension.Callback {

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.START);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        MainRecyclerAdapter.ItemBaseViewHolder holder = (MainRecyclerAdapter.ItemBaseViewHolder) viewHolder;
        if (viewHolder instanceof MainRecyclerAdapter.ItemSwipeWithActionWidthNoSpringViewHolder) {
            if (dX < -holder.mActionContainer.getWidth()) {
                dX = -holder.mActionContainer.getWidth();
            }
            holder.mViewContent.setTranslationX(dX);
            return;
        }
        if (viewHolder instanceof MainRecyclerAdapter.ItemBaseViewHolder)
            holder.mViewContent.setTranslationX(dX);
    }
}

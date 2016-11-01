package tr.easolution.recyclerview.utils;

import android.view.View;

import tr.easolution.recyclerview.draggable.DraggableItemViewHolder;
import tr.easolution.recyclerview.draggable.annotation.DraggableItemStateFlags;

public abstract class AbstractDraggableSwipeableItemViewHolder extends AbstractSwipeableItemViewHolder implements DraggableItemViewHolder {
    @DraggableItemStateFlags
    private int mDragStateFlags;

    public AbstractDraggableSwipeableItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setDragStateFlags(@DraggableItemStateFlags int flags) {
        mDragStateFlags = flags;
    }

    @Override
    @DraggableItemStateFlags
    public int getDragStateFlags() {
        return mDragStateFlags;
    }
}

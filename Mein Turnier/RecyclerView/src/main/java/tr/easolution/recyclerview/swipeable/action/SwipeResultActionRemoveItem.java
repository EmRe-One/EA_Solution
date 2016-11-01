package tr.easolution.recyclerview.swipeable.action;

import tr.easolution.recyclerview.swipeable.RecyclerViewSwipeManager;

public abstract class SwipeResultActionRemoveItem extends SwipeResultAction {
    public SwipeResultActionRemoveItem() {
        super(RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_REMOVE_ITEM);
    }
}

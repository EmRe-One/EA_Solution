package tr.easolution.recyclerview.expandable;

import tr.easolution.recyclerview.draggable.ItemDraggableRange;

public class GroupPositionItemDraggableRange extends ItemDraggableRange {
    public GroupPositionItemDraggableRange(int start, int end) {
        super(start, end);
    }

    protected String getClassName() {
        return "GroupPositionItemDraggableRange";
    }
}

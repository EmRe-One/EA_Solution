package tr.easolution.recyclerview.utils;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

public class CustomRecyclerViewUtils {
    public static final int ORIENTATION_UNKNOWN = -1;
    public static final int ORIENTATION_HORIZONTAL = OrientationHelper.HORIZONTAL; // = 0
    public static final int ORIENTATION_VERTICAL = OrientationHelper.VERTICAL; // = 1

    public static final int LAYOUT_TYPE_UNKNOWN = -1;
    public static final int LAYOUT_TYPE_LINEAR_HORIZONTAL = 0;
    public static final int LAYOUT_TYPE_LINEAR_VERTICAL = 1;
    public static final int LAYOUT_TYPE_GRID_HORIZONTAL = 2;
    public static final int LAYOUT_TYPE_GRID_VERTICAL = 3;
    public static final int LAYOUT_TYPE_STAGGERED_GRID_HORIZONTAL = 4;
    public static final int LAYOUT_TYPE_STAGGERED_GRID_VERTICAL = 5;

    public static RecyclerView.ViewHolder findChildViewHolderUnderWithoutTranslation(@NonNull RecyclerView rv, float x, float y) {
        final View child = findChildViewUnderWithoutTranslation(rv, x, y);
        return (child != null) ? rv.getChildViewHolder(child) : null;
    }

    public static int getLayoutType(@NonNull RecyclerView rv) {
        return getLayoutType(rv.getLayoutManager());
    }

    public static int extractOrientation(int layoutType) {
        switch (layoutType) {
            case LAYOUT_TYPE_UNKNOWN:
                return ORIENTATION_UNKNOWN;
            case LAYOUT_TYPE_LINEAR_HORIZONTAL:
            case LAYOUT_TYPE_GRID_HORIZONTAL:
            case LAYOUT_TYPE_STAGGERED_GRID_HORIZONTAL:
                return ORIENTATION_HORIZONTAL;
            case LAYOUT_TYPE_LINEAR_VERTICAL:
            case LAYOUT_TYPE_GRID_VERTICAL:
            case LAYOUT_TYPE_STAGGERED_GRID_VERTICAL:
                return ORIENTATION_VERTICAL;
            default:
                throw new IllegalArgumentException("Unknown layout type (= " + layoutType + ")");
        }
    }

    public static int getLayoutType(@Nullable RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            if (((GridLayoutManager) layoutManager).getOrientation() == GridLayoutManager.HORIZONTAL) {
                return LAYOUT_TYPE_GRID_HORIZONTAL;
            } else {
                return LAYOUT_TYPE_GRID_VERTICAL;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                return LAYOUT_TYPE_LINEAR_HORIZONTAL;
            } else {
                return LAYOUT_TYPE_LINEAR_VERTICAL;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (((StaggeredGridLayoutManager) layoutManager).getOrientation() == StaggeredGridLayoutManager.HORIZONTAL) {
                return LAYOUT_TYPE_STAGGERED_GRID_HORIZONTAL;
            } else {
                return LAYOUT_TYPE_STAGGERED_GRID_VERTICAL;
            }
        } else {
            return LAYOUT_TYPE_UNKNOWN;
        }
    }

    private static View findChildViewUnderWithoutTranslation(@NonNull ViewGroup parent, float x, float y) {
        final int count = parent.getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final View child = parent.getChildAt(i);
            if (x >= child.getLeft() &&
                    x <= child.getRight() &&
                    y >= child.getTop() &&
                    y <= child.getBottom()) {
                return child;
            }
        }
        return null;
    }

    public static RecyclerView.ViewHolder findChildViewHolderUnderWithTranslation(@NonNull RecyclerView rv, float x, float y) {
        final View child = rv.findChildViewUnder(x, y);
        return (child != null) ? rv.getChildViewHolder(child) : null;
    }

    public static Rect getLayoutMargins(View v, Rect outMargins) {
        final ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            outMargins.left = marginLayoutParams.leftMargin;
            outMargins.right = marginLayoutParams.rightMargin;
            outMargins.top = marginLayoutParams.topMargin;
            outMargins.bottom = marginLayoutParams.bottomMargin;
        } else {
            outMargins.left = outMargins.right = outMargins.top = outMargins.bottom = 0;
        }
        return outMargins;
    }

    public static Rect getDecorationOffsets(@NonNull RecyclerView.LayoutManager layoutManager, View view, Rect outDecorations) {
        outDecorations.left = layoutManager.getLeftDecorationWidth(view);
        outDecorations.right = layoutManager.getRightDecorationWidth(view);
        outDecorations.top = layoutManager.getTopDecorationHeight(view);
        outDecorations.bottom = layoutManager.getBottomDecorationHeight(view);

        return outDecorations;
    }

    public static Rect getViewBounds(@NonNull View v, @NonNull Rect outBounds) {
        outBounds.left = v.getLeft();
        outBounds.right = v.getRight();
        outBounds.top = v.getTop();
        outBounds.bottom = v.getBottom();
        return outBounds;
    }


    public static int findFirstVisibleItemPosition(@NonNull RecyclerView rv, boolean includesPadding) {
        RecyclerView.LayoutManager layoutManager = rv.getLayoutManager();

        if (layoutManager instanceof LinearLayoutManager) {
            if (includesPadding) {
                return findFirstVisibleItemPositionIncludesPadding((LinearLayoutManager) layoutManager);
            } else {
                return (((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition());
            }
        } else {
            return RecyclerView.NO_POSITION;
        }
    }

    public static int findLastVisibleItemPosition(@NonNull RecyclerView rv, boolean includesPadding) {
        RecyclerView.LayoutManager layoutManager = rv.getLayoutManager();

        if (layoutManager instanceof LinearLayoutManager) {
            if (includesPadding) {
                return findLastVisibleItemPositionIncludesPadding((LinearLayoutManager) layoutManager);
            } else {
                return (((LinearLayoutManager) layoutManager).findLastVisibleItemPosition());
            }
        } else {
            return RecyclerView.NO_POSITION;
        }
    }

    public static int findFirstCompletelyVisibleItemPosition(@NonNull RecyclerView rv) {
        RecyclerView.LayoutManager layoutManager = rv.getLayoutManager();

        if (layoutManager instanceof LinearLayoutManager) {
            return (((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition());
        } else {
            return RecyclerView.NO_POSITION;
        }
    }

    public static int findLastCompletelyVisibleItemPosition(@NonNull RecyclerView rv) {
        RecyclerView.LayoutManager layoutManager = rv.getLayoutManager();

        if (layoutManager instanceof LinearLayoutManager) {
            return (((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition());
        } else {
            return RecyclerView.NO_POSITION;
        }
    }

    public static int getSynchronizedPosition(@NonNull RecyclerView.ViewHolder holder) {
        int pos1 = holder.getLayoutPosition();
        int pos2 = holder.getAdapterPosition();
        if (pos1 == pos2) {
            return pos1;
        } else {
            return RecyclerView.NO_POSITION;
        }
    }

    public static int getSpanCount(@NonNull RecyclerView rv) {
        RecyclerView.LayoutManager layoutManager = rv.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        } else {
            return 1;
        }
    }

    public static int getOrientation(@NonNull RecyclerView rv) {
        RecyclerView.LayoutManager layoutManager = rv.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        } else {
            return ORIENTATION_UNKNOWN;
        }
    }

    private static int findFirstVisibleItemPositionIncludesPadding(LinearLayoutManager lm) {
        final View child = findOneVisibleChildIncludesPadding(lm, 0, lm.getChildCount(), false, true);
        return child == null ? RecyclerView.NO_POSITION : lm.getPosition(child);
    }

    private static int findLastVisibleItemPositionIncludesPadding(LinearLayoutManager lm) {
        final View child = findOneVisibleChildIncludesPadding(lm, lm.getChildCount() - 1, -1, false, true);
        return child == null ? RecyclerView.NO_POSITION : lm.getPosition(child);
    }

    // This method is a modified version of the LinearLayoutManager.findOneVisibleChild().
    private static View findOneVisibleChildIncludesPadding(
            LinearLayoutManager lm, int fromIndex, int toIndex,
            boolean completelyVisible, boolean acceptPartiallyVisible) {
        boolean isVertical = (lm.getOrientation() == LinearLayoutManager.VERTICAL);
        final int start = 0;
        final int end = (isVertical) ? lm.getHeight() : lm.getWidth();
        final int next = toIndex > fromIndex ? 1 : -1;
        View partiallyVisible = null;
        for (int i = fromIndex; i != toIndex; i += next) {
            final View child = lm.getChildAt(i);
            final int childStart = (isVertical) ? child.getTop() : child.getLeft();
            final int childEnd = (isVertical) ? child.getBottom() : child.getRight();
            if (childStart < end && childEnd > start) {
                if (completelyVisible) {
                    if (childStart >= start && childEnd <= end) {
                        return child;
                    } else if (acceptPartiallyVisible && partiallyVisible == null) {
                        partiallyVisible = child;
                    }
                } else {
                    return child;
                }
            }
        }
        return partiallyVisible;
    }
}
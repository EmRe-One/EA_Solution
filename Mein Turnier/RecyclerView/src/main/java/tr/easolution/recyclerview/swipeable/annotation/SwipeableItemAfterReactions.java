package tr.easolution.recyclerview.swipeable.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import tr.easolution.recyclerview.swipeable.SwipeableItemConstants;

@IntDef(flag = true, value = {
        SwipeableItemConstants.AFTER_SWIPE_REACTION_DEFAULT,
        SwipeableItemConstants.AFTER_SWIPE_REACTION_MOVE_TO_SWIPED_DIRECTION,
        SwipeableItemConstants.AFTER_SWIPE_REACTION_REMOVE_ITEM,
})
@Retention(RetentionPolicy.SOURCE)
public @interface SwipeableItemAfterReactions {
}

package tr.easolution.recyclerview.swipeable.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import tr.easolution.recyclerview.swipeable.SwipeableItemConstants;

@IntDef(flag = false, value = {
        SwipeableItemConstants.RESULT_NONE,
        SwipeableItemConstants.RESULT_CANCELED,
        SwipeableItemConstants.RESULT_SWIPED_LEFT,
        SwipeableItemConstants.RESULT_SWIPED_UP,
        SwipeableItemConstants.RESULT_SWIPED_RIGHT,
        SwipeableItemConstants.RESULT_SWIPED_DOWN,
})
@Retention(RetentionPolicy.SOURCE)
public @interface SwipeableItemResults {
}

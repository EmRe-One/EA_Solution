package tr.easolution.meinturnier.lib.RecyclerView.common.data;

import java.util.LinkedList;
import java.util.List;

import tr.easolution.meinturnier.lib.Storage;
import tr.easolution.meinturnier.lib.Turnier.Turnier;
import tr.easolution.meinturnier.lib.Turnier.Turniertyp;

/**
 * Created by Emre Ak on 28.10.2016.
 */

public class TurnierDataProvider extends AbstractDataProvider {

    private List<Turnier> mData;
    private Turnier mLastRemovedData;
    private int mLastRemovedPosition = -1;

    public TurnierDataProvider(){
        mData = Storage.turniere;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Data getItem(int index) {
        if (index < 0 || index >= getCount()) {
            throw new IndexOutOfBoundsException("index = " + index);
        }

        return mData.get(index);
    }

    @Override
    public void removeItem(int position) {
        //noinspection UnnecessaryLocalVariable
        final Turnier removedItem = mData.remove(position);

        mLastRemovedData = removedItem;
        mLastRemovedPosition = position;
    }

    @Override
    public void moveItem(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }

        final Turnier item = mData.remove(fromPosition);

        mData.add(toPosition, item);
        mLastRemovedPosition = -1;
    }

    @Override
    public int undoLastRemoval() {
        if (mLastRemovedData != null) {
            int insertedPosition;
            if (mLastRemovedPosition >= 0 && mLastRemovedPosition < mData.size()) {
                insertedPosition = mLastRemovedPosition;
            } else {
                insertedPosition = mData.size();
            }

            mData.add(insertedPosition, mLastRemovedData);

            mLastRemovedData = null;
            mLastRemovedPosition = -1;

            return insertedPosition;
        } else {
            return -1;
        }
    }
}

package tr.easolution.meinturnier.lib.Turnier;

import android.util.JsonWriter;

import java.io.IOException;
import java.util.List;

import tr.easolution.meinturnier.lib.RecyclerView.common.data.AbstractDataProvider;

/**
 * Created by Emre Ak on 26.10.2016.
 */

public class Spieler extends AbstractDataProvider.Data {

    private long mId;
    private String mName;
    private int mTore;
    private boolean mPinned = false;
    private final int mViewType = 0;


    public Spieler(long mId, String mName, int mTore) {
        this.mId = mId;
        this.mName = mName;
        this.mTore = mTore;
    }

    @Override
    public long getId() {
        return mId;
    }

    public void setId(long mId){
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getTore() {
        return mTore;
    }

    public void setTore(int tore) {
        this.mTore = tore;
    }

    @Override
    public boolean isSectionHeader() {
        return false;
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public String getText() {
        return mName;
    }

    @Override
    public void setPinned(boolean pinned) {
        mPinned = pinned;
    }

    @Override
    public boolean isPinned() {
        return mPinned;
    }

}

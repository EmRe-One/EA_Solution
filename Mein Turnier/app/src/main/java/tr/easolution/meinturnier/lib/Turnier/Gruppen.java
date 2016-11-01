package tr.easolution.meinturnier.lib.Turnier;

import java.util.List;

import tr.easolution.meinturnier.lib.RecyclerView.common.data.AbstractDataProvider;

/**
 * Created by Emre Ak on 26.10.2016.
 */

public class Gruppen extends AbstractDataProvider.Data{

    private long mId;
    private String mGruppenName;
    private List<Team> mMannschaften;
    private boolean mPinned = false;
    private final int mViewType = 0;

    public Gruppen(long mId, String mGruppenName, List<Team> mMannschaften){
        this.mId = mId;
        this.mGruppenName = mGruppenName;
        this.mMannschaften = mMannschaften;
    }


    @Override
    public long getId() {
        return this.mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public String getGruppenName() {
        return mGruppenName;
    }

    public void setGruppenName(String mGruppenName) {
        this.mGruppenName = mGruppenName;
    }

    public List<Team> getMannschaften() {
        return mMannschaften;
    }

    public void setMannschaften(List<Team> mMannschaften) {
        this.mMannschaften = mMannschaften;
    }


    @Override
    public boolean isSectionHeader() {
        return false;
    }

    @Override
    public int getViewType() {
        return mViewType;
    }

    @Override
    public String getText() {
        return mGruppenName;
    }

    @Override
    public void setPinned(boolean pinned) {
        this.mPinned = pinned;
    }

    @Override
    public boolean isPinned() {
        return mPinned;
    }
}

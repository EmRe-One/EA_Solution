package tr.easolution.meinturnier.lib.Turnier;

import java.util.List;

import tr.easolution.meinturnier.lib.RecyclerView.common.data.AbstractDataProvider;

/**
 * Created by Emre Ak on 26.10.2016.
 */

public class Team extends AbstractDataProvider.Data{

    private long mId;
    private String mTeamName;
    private int mGesamtspiele, mSiege, mNiederlagen, mTore, mGegenTore;
    private int mLogoResource;
    private List<Spieler> mSpieler;
    private boolean mPinned = false;
    private final int mViewType = 0;

    public Team(long mId, String mTeamName){
        this(mId, mTeamName, 0, 0, 0, 0, 0, 0);
    }

    public Team(long mId, String mTeamName, int mGesamtspiele, int mSiege, int mNiederlagen, int mTore, int mGegenTore, int mLogoResource) {
        this(mId, mTeamName, mGesamtspiele, mSiege, mNiederlagen, mTore, mGegenTore, mLogoResource, null);
    }

    public Team(long mId, String mTeamName, int mGesamtspiele, int mSiege, int mNiederlagen, int mTore, int mGegenTore, int mLogoResource, List<Spieler> mSpieler) {
        this.mId = mId;
        this.mTeamName = mTeamName;
        this.mGesamtspiele = mGesamtspiele;
        this.mSiege = mSiege;
        this.mNiederlagen = mNiederlagen;
        this.mTore = mTore;
        this.mGegenTore = mGegenTore;
        this.mLogoResource = mLogoResource;
        this.mSpieler = mSpieler;
    }

    @Override
    public long getId() {
        return mId;
    }

    public void setId(long mId){
        this.mId = mId;
    }

    public String getTeamName() {
        return mTeamName;
    }

    public void setTeamName(String mTeamName) {
        this.mTeamName = mTeamName;
    }

    public int getGesamtspiele() {
        return mGesamtspiele;
    }

    public void setGesamtspiele(int mGesamtspiele) {
        this.mGesamtspiele = mGesamtspiele;
    }

    public int getSiege() {
        return mSiege;
    }

    public void setSiege(int mSiege) {
        this.mSiege = mSiege;
    }

    public int getNiederlagen() {
        return mNiederlagen;
    }

    public void setNiederlagen(int mNiederlagen) {
        this.mNiederlagen = mNiederlagen;
    }

    public int getTore() {
        return mTore;
    }

    public void setTore(int mTore) {
        this.mTore = mTore;
    }

    public int getGegenTore() {
        return mGegenTore;
    }

    public void setGegenTore(int mGegenTore) {
        this.mGegenTore = mGegenTore;
    }

    public int getLogoResource() {
        return mLogoResource;
    }

    public void setLogoResource(int mLogoResource) {
        this.mLogoResource = mLogoResource;
    }

    public List<Spieler> getSpieler() {
        return mSpieler;
    }

    public void setSpieler(List<Spieler> mSpieler) {
        this.mSpieler = mSpieler;
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
        return mTeamName;
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

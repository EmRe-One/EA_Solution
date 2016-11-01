package tr.easolution.meinturnier.lib.Turnier;

import java.util.ArrayList;
import java.util.List;

import tr.easolution.meinturnier.lib.RecyclerView.common.data.AbstractDataProvider;
import tr.easolution.recyclerview.swipeable.RecyclerViewSwipeManager;

/**
 * Created by Emre Ak on 26.10.2016.
 */

public class Turnier extends AbstractDataProvider.Data {

    private long mId;
    private String mTurnierName;
    private Turniertyp mTurniertyp;
    private TurnierHinRueck mTurnierHinRueck;
    private List<Gruppen> mGruppen;
    private boolean mPinned = false;
    private final int mViewType = 0;
    private final int swipeReaction = RecyclerViewSwipeManager.REACTION_CAN_SWIPE_UP | RecyclerViewSwipeManager.REACTION_CAN_SWIPE_DOWN;


    public Turnier(long mId, String mTurnierName){
        this(mId, mTurnierName, Turniertyp.GRUPPE_KO_SYSTEM, TurnierHinRueck.HIN);
    }

    public Turnier(long mId, String mTurnierName, Turniertyp typ, TurnierHinRueck mTurnierHinRueck){
        this(mId, mTurnierName, typ, mTurnierHinRueck, new ArrayList<Gruppen>());
    }

    public Turnier(long mId, String mTurnierName, Turniertyp mTurniertyp, TurnierHinRueck mTurnierHinRueck, List<Gruppen> mGruppen){
        this.mId = mId;
        this.mTurnierName = mTurnierName;
        this.mTurniertyp = mTurniertyp;
        this.mTurnierHinRueck = mTurnierHinRueck;
        this.mGruppen = mGruppen;
    }

    @Override
    public long getId() {
        return mId;
    }

    public void setId(int mId){
        this.mId = mId;
    }

    public String getTurnierName() {
        return mTurnierName;
    }

    public void setTurnierName(String mTurnierName) {
        this.mTurnierName = mTurnierName;
    }

    public Turniertyp getTurniertyp() {
        return mTurniertyp;
    }

    public void setTurniertyp(String mTurniertyp) {
        this.mTurniertyp = Turniertyp.getTyp(mTurniertyp);
    }

    public TurnierHinRueck getTurnierHinRueck(){
        return mTurnierHinRueck;
    }

    public void setmTurnierHinRueck(String typ){
        this.mTurnierHinRueck = TurnierHinRueck.getTyp(typ);
    }

    public List<Gruppen> getGruppen() {
        return mGruppen;
    }

    public void setGruppen(List<Gruppen> mGruppen) {
        this.mGruppen = mGruppen;
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
        return mTurnierName;
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

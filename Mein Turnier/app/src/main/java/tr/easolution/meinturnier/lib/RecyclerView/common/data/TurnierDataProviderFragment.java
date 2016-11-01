package tr.easolution.meinturnier.lib.RecyclerView.common.data;

import android.support.v4.app.Fragment;
import android.os.Bundle;

/**
 * Created by Emre Ak on 28.10.2016.
 */

public class TurnierDataProviderFragment extends Fragment {
    private AbstractDataProvider mDataProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);  // keep the mDataProvider instance
        mDataProvider = new TurnierDataProvider();
    }

    public AbstractDataProvider getDataProvider() {
        return mDataProvider;
    }

}

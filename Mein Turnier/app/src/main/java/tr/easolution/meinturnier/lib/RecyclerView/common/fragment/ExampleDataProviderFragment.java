package tr.easolution.meinturnier.lib.RecyclerView.common.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import tr.easolution.meinturnier.lib.RecyclerView.common.data.AbstractDataProvider;
import tr.easolution.meinturnier.lib.RecyclerView.common.data.ExampleDataProvider;


public class ExampleDataProviderFragment extends Fragment {
    private AbstractDataProvider mDataProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);  // keep the mDataProvider instance
        mDataProvider = new ExampleDataProvider();
    }

    public AbstractDataProvider getDataProvider() {
        return mDataProvider;
    }
}

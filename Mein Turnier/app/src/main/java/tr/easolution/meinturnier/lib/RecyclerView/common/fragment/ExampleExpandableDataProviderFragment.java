package tr.easolution.meinturnier.lib.RecyclerView.common.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import tr.easolution.meinturnier.lib.RecyclerView.common.data.AbstractExpandableDataProvider;
import tr.easolution.meinturnier.lib.RecyclerView.common.data.ExampleExpandableDataProvider;


public class ExampleExpandableDataProviderFragment extends Fragment {
    private ExampleExpandableDataProvider mDataProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);  // keep the mDataProvider instance
        mDataProvider = new ExampleExpandableDataProvider();
    }

    public AbstractExpandableDataProvider getDataProvider() {
        return mDataProvider;
    }
}

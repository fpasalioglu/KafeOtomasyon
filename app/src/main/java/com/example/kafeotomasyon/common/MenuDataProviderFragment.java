package com.example.kafeotomasyon.common;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class MenuDataProviderFragment extends Fragment {
    private MenuDataProvider mDataProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);  // keep the mDataProvider instance
        mDataProvider = new MenuDataProvider();
    }

    public AbstractMenuDataProvider getDataProvider() {
        return mDataProvider;
    }
}
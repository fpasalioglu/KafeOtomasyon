package com.example.kafeotomasyon.common;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.kafeotomasyon.R;

public class MenuActivity extends AppCompatActivity {
    private static final String FRAGMENT_TAG_DATA_PROVIDER = "data provider";
    private static final String FRAGMENT_LIST_VIEW = "list view";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(new MenuDataProviderFragment(), FRAGMENT_TAG_DATA_PROVIDER)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MenuFragment(), FRAGMENT_LIST_VIEW)
                    .commit();
        }
    }

    public AbstractMenuDataProvider getDataProvider() {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_DATA_PROVIDER);
        return ((MenuDataProviderFragment) fragment).getDataProvider();
    }
}
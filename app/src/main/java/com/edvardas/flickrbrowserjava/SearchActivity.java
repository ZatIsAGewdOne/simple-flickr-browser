package com.edvardas.flickrbrowserjava;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;

public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        activateToolbar(true);
        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: starts");
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        SearchableInfo searchableInfo = manager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
//        Log.d(TAG, "onCreateOptionsMenu: " + getComponentName().toString());
//        Log.d(TAG, "onCreateOptionsMenu: hint is " + searchView.getQueryHint());
//        Log.d(TAG, "onCreateOptionsMenu: searchable info is " + searchableInfo.toString());
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit: starts");
                searchView.clearFocus();
                finish();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        Log.d(TAG, "onCreateOptionsMenu: returned " + true);
        return true;
    }
}
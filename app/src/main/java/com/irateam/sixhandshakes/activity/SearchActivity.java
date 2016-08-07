package com.irateam.sixhandshakes.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.irateam.sixhandshakes.R;
import com.irateam.sixhandshakes.ui.RecyclerViewAdapter;
import com.irateam.sixhandshakes.utils.SimpleCallback;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.methods.VKApiUsers;
import com.vk.sdk.api.model.VKApiUserFull;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private final VKApiUsers VKUsers = new VKApiUsers();

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        adapter = new RecyclerViewAdapter(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem itemSearch = menu.findItem(R.id.action_search);
        itemSearch.expandActionView();
        MenuItemCompat.setOnActionExpandListener(itemSearch, new ActionExpandListener());

        SearchView searchView = (SearchView) itemSearch.getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchQueryListener());

        search("");
        return true;
    }

    public void search(String q) {
        VKParameters params = VKParameters.from(
                VKApiConst.FIELDS, "photo_100, photo_200",
                VKApiConst.Q, q);

        VKUsers.search(params).executeWithListener(new SimpleCallback(response -> {
            List<VKApiUserFull> friends = new ArrayList<>();
            JSONArray array = response.json.getJSONObject("response").getJSONArray("items");
            for (int i = 0; i < array.length(); i++) {
                friends.add(new VKApiUserFull(array.getJSONObject(i)));
            }
            adapter.setFriends(friends);
            adapter.notifyDataSetChanged();
        }));
    }

    private class SearchQueryListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            search(query);
            return true;
        }
    }

    private class ActionExpandListener implements MenuItemCompat.OnActionExpandListener {

        @Override
        public boolean onMenuItemActionExpand(MenuItem item) {
            return false;
        }

        @Override
        public boolean onMenuItemActionCollapse(MenuItem item) {
            finish();
            return false;
        }
    }
}

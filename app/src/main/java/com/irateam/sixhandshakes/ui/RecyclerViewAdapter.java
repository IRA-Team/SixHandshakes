package com.irateam.sixhandshakes.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irateam.sixhandshakes.R;
import com.irateam.sixhandshakes.activity.SearchActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.vk.sdk.api.model.VKApiUserFull;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<FriendViewHolder> {

    private List<VKApiUserFull> friends = new ArrayList<>();
    private SearchActivity searchActivity;
    DisplayImageOptions options;

    public RecyclerViewAdapter(SearchActivity activity) {
        searchActivity = activity;
        options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(10000))
                .build();
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element, parent, false);
        return new FriendViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        VKApiUserFull user = friends.get(position);
        holder.name.setText(user.first_name + " " + user.last_name);
        holder.itemView.setOnClickListener(v -> {
            searchActivity.setResult(Activity.RESULT_OK, new Intent().putExtra("user", user));
            searchActivity.finish();
        });
        ImageLoader.getInstance().displayImage(user.photo_50, holder.photo, options);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public List<VKApiUserFull> getFriends() {
        return friends;
    }

    public void setFriends(List<VKApiUserFull> friends) {
        this.friends = friends;
    }
}

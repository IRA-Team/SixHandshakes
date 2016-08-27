package com.irateam.sixhandshakes.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irateam.sixhandshakes.R;
import com.irateam.sixhandshakes.ui.viewholder.UserViewHolder;
import com.vk.sdk.api.model.VKApiUserFull;

import java.util.ArrayList;
import java.util.List;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<VKApiUserFull> users = new ArrayList<>();
    private OnUserClickListener userClickListener;

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final VKApiUserFull user = users.get(position);
        holder.setUser(user);
        holder.itemView.setOnClickListener(v -> notifyOnUserClicked(user));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public List<VKApiUserFull> getUsers() {
        return users;
    }

    public void setUsers(List<VKApiUserFull> users) {
        this.users = users;
    }

    public void setOnUserClickListener(OnUserClickListener userClickListener) {
        this.userClickListener = userClickListener;
    }

    private void notifyOnUserClicked(VKApiUserFull user) {
        if (userClickListener != null) {
            userClickListener.onUserClicked(user);
        }
    }

    public interface OnUserClickListener {

        void onUserClicked(VKApiUserFull user);
    }
}

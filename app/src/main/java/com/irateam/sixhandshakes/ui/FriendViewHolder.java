package com.irateam.sixhandshakes.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.irateam.sixhandshakes.R;

public class FriendViewHolder extends RecyclerView.ViewHolder {

    ImageView photo;
    TextView name;

    public FriendViewHolder(View view) {
        super(view);
        photo = (ImageView) view.findViewById(R.id.photo);
        name = (TextView) view.findViewById(R.id.name);
    }
}

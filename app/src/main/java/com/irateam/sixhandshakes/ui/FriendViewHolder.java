package com.irateam.sixhandshakes.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.irateam.sixhandshakes.R;

public class FriendViewHolder extends RecyclerView.ViewHolder {

    public final ImageView photo;
    public final TextView name;
    public final TextView id;

    public FriendViewHolder(View view) {
        super(view);
        setIsRecyclable(false);
        photo = (ImageView) view.findViewById(R.id.photo);
        name = (TextView) view.findViewById(R.id.name);
        id = (TextView) view.findViewById(R.id.id);
    }


}

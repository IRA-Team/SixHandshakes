package com.irateam.sixhandshakes.binding;

import android.databinding.BindingAdapter;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.irateam.sixhandshakes.R;
import com.irateam.sixhandshakes.ui.AnimatedRoundedDisplayer;
import com.irateam.sixhandshakes.utils.VKUtils;
import com.irateam.sixhandshakes.viewmodel.MainActivityVM;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vk.sdk.api.model.VKApiUser;

public final class BindingAdapters {

    private static final DisplayImageOptions options;

    static {
        options = new DisplayImageOptions.Builder()
                .displayer(new AnimatedRoundedDisplayer(10000))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @BindingAdapter("bind:onClick")
    public static void onClick(View view, Runnable runnable) {
        view.setOnClickListener(v -> runnable.run());
    }

    @BindingAdapter("android:visibility")
    public static void visibility(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("app:user")
    public static void displayPhotoAsyncById(ImageView target, VKApiUser user) {
        if (user != null) {
            ImageLoader.getInstance().displayImage(user.photo_200, target, options);
            target.setOnClickListener(v -> VKUtils.openVKProfile(v.getContext(), user.id));
        } else {
            target.setImageResource(0);
        }
    }

    @BindingAdapter("app:user")
    public static void visibility(TextView view, VKApiUser user) {
        if (user != null) {
            view.setText(user.first_name + " " + user.last_name);
            view.setOnClickListener(v -> VKUtils.openVKProfile(v.getContext(), user.id));
        } else {
            view.setOnClickListener(null);
        }
    }

    @BindingAdapter("app:state")
    public static void state(FloatingActionButton fab, MainActivityVM.State state) {
        switch (state) {
            case NONE:
                fab.setVisibility(View.GONE);
                break;

            case READY:
                fab.setVisibility(View.VISIBLE);
                fab.setImageResource(R.drawable.ic_fab_play_blue_24dp);
                break;

            case PROCESSING:
                fab.setVisibility(View.VISIBLE);
                fab.setImageResource(R.drawable.ic_fab_stop_blue_24dp);
                break;

            case FINISHED:
                fab.setVisibility(View.VISIBLE);
                break;
        }
    }

}

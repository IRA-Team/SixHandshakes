package com.irateam.sixhandshakes.binding.adapter;

import android.databinding.BindingAdapter;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.irateam.sixhandshakes.R;
import com.irateam.sixhandshakes.viewmodel.MainActivityVM;

public final class FloatingActionButtonBindingAdapters {

    private FloatingActionButtonBindingAdapters() {
        throw new AssertionError(getClass().getName() + " can't be instantiated");
    }

    @BindingAdapter("app:state")
    public static void bindState(FloatingActionButton target, MainActivityVM.State state) {
        switch (state) {
            case NONE:
                target.setVisibility(View.GONE);
                break;

            case READY:
                target.setVisibility(View.VISIBLE);
                target.setImageResource(R.drawable.ic_fab_play_blue_24dp);
                break;

            case PROCESSING:
                target.setVisibility(View.VISIBLE);
                target.setImageResource(R.drawable.ic_fab_stop_blue_24dp);
                break;

            case FINISHED:
                target.setVisibility(View.VISIBLE);
                break;
        }
    }
}

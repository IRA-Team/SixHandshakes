package com.irateam.sixhandshakes.binding.adapter;

import android.databinding.BindingAdapter;
import android.view.View;

public final class BindingAdapters {

    private BindingAdapters() {
        throw new AssertionError(getClass().getName() + " can't be instantiated");
    }

    @BindingAdapter("app:onClick")
    public static void bindOnClick(View target, Runnable runnable) {
        target.setOnClickListener(v -> runnable.run());
    }

    @BindingAdapter("android:visibility")
    public static void bindVisibility(View target, boolean isVisible) {
        target.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}

package com.irateam.sixhandshakes.binding.adapter;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.irateam.sixhandshakes.util.VKUtils;
import com.vk.sdk.api.model.VKApiUser;

public final class TextViewBindingAdapters {

    private TextViewBindingAdapters() {
        throw new AssertionError(getClass().getName() + " can't be instantiated");
    }

    @BindingAdapter("app:user")
    public static void bindUser(TextView target, VKApiUser user) {
        if (user != null) {
            target.setText(user.first_name + " " + user.last_name);
            target.setOnClickListener(v -> VKUtils.openVKProfile(v.getContext(), user.id));
        } else {
            target.setOnClickListener(null);
        }
    }
}

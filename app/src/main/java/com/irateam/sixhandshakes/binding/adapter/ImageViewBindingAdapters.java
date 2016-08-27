package com.irateam.sixhandshakes.binding.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.irateam.sixhandshakes.util.VKUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vk.sdk.api.model.VKApiUser;

public final class ImageViewBindingAdapters {

    private ImageViewBindingAdapters() {
        throw new AssertionError(getClass().getName() + " can't be instantiated");
    }

    @BindingAdapter("app:url")
    public static void bindURL(ImageView target, String url) {
        ImageLoader.getInstance().displayImage(url, target);
    }

    @BindingAdapter("app:user")
    public static void bindUser(ImageView target, VKApiUser user) {
        if (user != null) {
            ImageLoader.getInstance().displayImage(user.photo_200, target);
            target.setOnClickListener(v -> VKUtils.openVKProfile(v.getContext(), user.id));
        } else {
            target.setImageResource(0);
        }
    }
}

package com.irateam.sixhandshakes;

import com.irateam.sixhandshakes.util.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vk.sdk.VKSdk;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(ImageLoaderUtils.DEFAULT_IMAGE_OPTIONS)
                .build();

        ImageLoader.getInstance().init(config);
    }
}

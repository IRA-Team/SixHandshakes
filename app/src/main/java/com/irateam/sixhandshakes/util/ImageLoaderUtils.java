package com.irateam.sixhandshakes.util;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public final class ImageLoaderUtils {

    public static final DisplayImageOptions DEFAULT_IMAGE_OPTIONS;

    static {
        DEFAULT_IMAGE_OPTIONS = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(10000))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    private ImageLoaderUtils() {
        throw new AssertionError("ImageLoaderUtils class can't be instantiated");
    }

}

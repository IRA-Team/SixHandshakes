package com.irateam.sixhandshakes.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

public class VKUtils {

    public static final String VK_PACKAGE_URI = "com.vkontakte.android";
    public static final String VK_URL = "http://vk.com/";

    public static void openVKProfile(Context context, int vkUserId) {
        if (isVKAppInstalled(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vkontakte://profile/" + vkUserId));
            context.startActivity(intent);
        } else {
            String url = VK_URL + "id" + vkUserId;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        }
    }

    public static boolean isVKAppInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean isInstalled;
        try {
            pm.getPackageInfo(VK_PACKAGE_URI, PackageManager.GET_ACTIVITIES);
            isInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            isInstalled = false;
        }
        return isInstalled;
    }
}

package com.irateam.sixhandshakes.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.irateam.sixhandshakes.R;
import com.irateam.sixhandshakes.service.RequestService;
import com.irateam.sixhandshakes.binding.BindingActivity;
import com.irateam.sixhandshakes.viewmodel.MainActivityVM;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKApiUserFull;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends BindingActivity<MainActivityVM> {

    @Override
    public int onCreateBinding() {
        return R.layout.activity_main;
    }

    @Override
    public MainActivityVM onCreateViewModel() {
        return new MainActivityVM();
    }

}

package com.irateam.sixhandshakes.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.irateam.sixhandshakes.R;
import com.irateam.sixhandshakes.service.RequestService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
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

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    Button vk, github;
    FloatingActionButton fab;
    TextView selfName, targetName;

    VKApiUser selfUser, targetUser;

    DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);

        options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(10000))
                .build();

        selfUser = new VKApiUser();

        vk = (Button) findViewById(R.id.button_vk_login);
        github = (Button) findViewById(R.id.button_github);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        selfName = (TextView) findViewById(R.id.self_name);
        targetName = (TextView) findViewById(R.id.target_name);

        vk.setOnClickListener((v) -> {
            VKSdk.login(MainActivity.this, VKScope.FRIENDS);
        });
        github.setOnClickListener((v) -> {
            Uri uri = Uri.parse("https://github.com/IRA-Team/SixHandshakes");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        findViewById(R.id.target).setOnClickListener(v -> {
            startActivityForResult(new Intent(MainActivity.this, SearchActivity.class), 228);
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                VKSdk.logout();
                vk.setVisibility(View.VISIBLE);
                github.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                vk.setVisibility(View.GONE);
                github.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setTitle(getResources().getString(R.string.logout));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                startService(new Intent(MainActivity.this, RequestService.class));
                bindService(new Intent(MainActivity.this, RequestService.class), MainActivity.this, 0);

                VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_400_orig")).executeSyncWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        try {
                            JSONArray jsonArray = response.json.getJSONArray("response");
                            selfUser.id = jsonArray.getJSONObject(0).getInt("id");
                            selfUser.first_name = jsonArray.getJSONObject(0).getString("first_name");
                            selfUser.last_name = jsonArray.getJSONObject(0).getString("last_name");
                            selfUser.photo_400_orig = jsonArray.getJSONObject(0).getString("photo_400_orig");
                            selfName.setText(selfUser.first_name + " " + selfUser.last_name);
                            ViewGroup view = (ViewGroup) findViewById(R.id.target);
                            ImageLoader.getInstance().displayImage(selfUser.photo_400_orig, (ImageView) view.findViewById(R.id.image), options);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onError(VKError error) {
            }
        })) ;

        if (requestCode == 228) {
            VKApiUserFull user = data.getParcelableExtra("user");
            ViewGroup view = (ViewGroup) findViewById(R.id.target);
            ImageLoader.getInstance().displayImage(user.photo_100, (ImageView) view.findViewById(R.id.image), options);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        ((RequestService.RequestServiceBinder) service).getService().start(3664185);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}

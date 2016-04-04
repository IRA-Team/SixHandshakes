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
import android.widget.Button;

import com.irateam.sixhandshakes.R;
import com.irateam.sixhandshakes.service.RequestService;
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

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    Button vk, github;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);

        vk = (Button) findViewById(R.id.button_vk_login);
        github = (Button) findViewById(R.id.button_github);
        fab = (FloatingActionButton) findViewById(R.id.fab);

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

                VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_400")).executeSyncWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {

                    }
                });
            }

            @Override
            public void onError(VKError error) {
            }
        })) ;

        if (requestCode == 228) {
            System.out.println(data.getParcelableArrayExtra("user"));
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

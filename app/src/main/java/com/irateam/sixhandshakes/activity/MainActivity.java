package com.irateam.sixhandshakes.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.irateam.sixhandshakes.R;
import com.irateam.sixhandshakes.service.RequestService;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_vk_login).setOnClickListener((v) -> {
            VKSdk.login(MainActivity.this, VKScope.FRIENDS);
        });
        findViewById(R.id.button_github).setOnClickListener((v) -> {
            Uri uri = Uri.parse("https://github.com/IRA-Team/SixHandshakes");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Log.e("KEKAN", "KEKAN");
                startService(new Intent(MainActivity.this, RequestService.class));
                bindService(new Intent(MainActivity.this, RequestService.class), MainActivity.this, 0);
            }

            @Override
            public void onError(VKError error) {
            }
        })) ;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        ((RequestService.RequestServiceBinder) service).getService().start(1);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}

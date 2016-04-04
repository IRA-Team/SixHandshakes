package com.irateam.sixhandshakes.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.irateam.sixhandshakes.R;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

public class MainActivity extends AppCompatActivity {

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
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        for (String s: fingerprints)
            Log.e("KEKAN", s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Log.e("KEKAN", "KEKAN");
                /*startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();*/
            }

            @Override
            public void onError(VKError error) {
            }
        })) ;
    }
}

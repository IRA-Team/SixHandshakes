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

    DisplayImageOptions options;
    VKApiUserFull targetUser;
    VKApiUser selfUser;

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

        fab.setOnClickListener(v -> {
            requestService.setListener(new RequestService.ResultListener() {
                @Override
                public void onPathFound(int[] ids) {
                    int size = ids.length;
                    switch (size) {
                        case 3:
                            findViewById(R.id.id1).setVisibility(View.VISIBLE);
                            VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_200", VKApiConst.USER_ID, ids[1])).executeWithListener(new VKRequest.VKRequestListener() {
                                @Override
                                public void onComplete(VKResponse response) {
                                    try {
                                        ImageLoader.getInstance().displayImage(response.json.getJSONArray("response").getJSONObject(0).getString("photo_200"), (ImageView) findViewById(R.id.image1), options);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            break;
                        case 4:
                            findViewById(R.id.id1).setVisibility(View.VISIBLE);
                            findViewById(R.id.id2).setVisibility(View.VISIBLE);
                            VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_200", VKApiConst.USER_ID, ids[1])).executeWithListener(new VKRequest.VKRequestListener() {
                                @Override
                                public void onComplete(VKResponse response) {
                                    try {
                                        ImageLoader.getInstance().displayImage(response.json.getJSONArray("response").getJSONObject(0).getString("photo_200"), (ImageView) findViewById(R.id.image1), options);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_200", VKApiConst.USER_ID, ids[2])).executeWithListener(new VKRequest.VKRequestListener() {
                                @Override
                                public void onComplete(VKResponse response) {
                                    try {
                                        ImageLoader.getInstance().displayImage(response.json.getJSONArray("response").getJSONObject(0).getString("photo_200"), (ImageView) findViewById(R.id.image2), options);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            break;
                        case 5:
                            findViewById(R.id.id1).setVisibility(View.VISIBLE);
                            findViewById(R.id.id2).setVisibility(View.VISIBLE);
                            findViewById(R.id.id3).setVisibility(View.VISIBLE);
                            VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_200", VKApiConst.USER_ID, ids[1])).executeWithListener(new VKRequest.VKRequestListener() {
                                @Override
                                public void onComplete(VKResponse response) {
                                    try {
                                        ImageLoader.getInstance().displayImage(response.json.getJSONArray("response").getJSONObject(0).getString("photo_200"), (ImageView) findViewById(R.id.image1), options);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            findViewById(R.id.id1).setVisibility(View.VISIBLE);
                            VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_200", VKApiConst.USER_ID, ids[2])).executeWithListener(new VKRequest.VKRequestListener() {
                                @Override
                                public void onComplete(VKResponse response) {
                                    try {
                                        ImageLoader.getInstance().displayImage(response.json.getJSONArray("response").getJSONObject(0).getString("photo_200"), (ImageView) findViewById(R.id.image2), options);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            findViewById(R.id.id1).setVisibility(View.VISIBLE);
                            VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_200", VKApiConst.USER_ID, ids[3])).executeWithListener(new VKRequest.VKRequestListener() {
                                @Override
                                public void onComplete(VKResponse response) {
                                    try {
                                        ImageLoader.getInstance().displayImage(response.json.getJSONArray("response").getJSONObject(0).getString("photo_200"), (ImageView) findViewById(R.id.image3), options);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            break;
                    }
                    System.out.println("FOUND");
                }

                @Override
                public void onNothingFound() {

                }
            });
            requestService.start(selfUser.id, targetUser.id);
            fab.hide();
        });

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

                VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_200")).executeSyncWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        try {
                            JSONArray jsonArray = response.json.getJSONArray("response");
                            selfUser.id = jsonArray.getJSONObject(0).getInt("id");
                            selfUser.first_name = jsonArray.getJSONObject(0).getString("first_name");
                            selfUser.last_name = jsonArray.getJSONObject(0).getString("last_name");
                            selfUser.photo_200 = jsonArray.getJSONObject(0).getString("photo_200");
                            selfName.setText(selfUser.first_name + " " + selfUser.last_name);
                            targetName.setText("Add User");
                            ViewGroup view = (ViewGroup) findViewById(R.id.self);
                            view.post(() -> {
                                ImageLoader.getInstance().displayImage(selfUser.photo_200, (ImageView) view.findViewById(R.id.self_image), options);
                            });
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
            targetUser = data.getParcelableExtra("user");
            ViewGroup view = (ViewGroup) findViewById(R.id.target);
            targetName.setText(targetUser.first_name + " " + targetUser.last_name);
            ImageLoader.getInstance().displayImage(targetUser.photo_200, (ImageView) view.findViewById(R.id.target_image), options);
        }
    }

    private RequestService requestService;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        requestService = ((RequestService.RequestServiceBinder) service).getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}

package com.irateam.sixhandshakes.viewmodel;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.IBinder;
import android.view.MenuItem;

import com.irateam.sixhandshakes.activity.MainActivity;
import com.irateam.sixhandshakes.activity.SearchActivity;
import com.irateam.sixhandshakes.binding.ActivityViewModel;
import com.irateam.sixhandshakes.databinding.ActivityMainBinding;
import com.irateam.sixhandshakes.service.RequestService;
import com.irateam.sixhandshakes.utils.SimpleCallback;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.model.VKApiUser;

public class MainActivityVM extends ActivityViewModel<MainActivity, ActivityMainBinding> implements RequestService.ResultListener, ServiceConnection {

    private final static int SEARCH_REQUEST_CODE = 228;

    private RequestService requestService;

    public ObservableBoolean isProcessing = new ObservableBoolean();
    public ObservableBoolean isLoggedIn = new ObservableBoolean();

    public ObservableField<VKApiUser> self = new ObservableField<>();
    public ObservableField<VKApiUser> first = new ObservableField<>();
    public ObservableField<VKApiUser> second = new ObservableField<>();
    public ObservableField<VKApiUser> third = new ObservableField<>();
    public ObservableField<VKApiUser> fourth = new ObservableField<>();
    public ObservableField<VKApiUser> target = new ObservableField<>();

    @Override
    public void initialize(MainActivity activity, ActivityMainBinding binding) {
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setElevation(0);
    }

    public void startProcessing() {
        getActivity().startService(new Intent(getActivity(), RequestService.class));
        getActivity().bindService(new Intent(getActivity(), RequestService.class), this, 0);
        isProcessing.set(true);
    }

    public void stopProcessing() {

    }

    public void clear() {
        first.set(null);
        second.set(null);
        third.set(null);
        fourth.set(null);
        target.set(null);
    }

    public void openSearch() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        getActivity().startActivityForResult(intent, SEARCH_REQUEST_CODE);
    }

    public void openLoginActivity() {
        VKSdk.login(getActivity(), VKScope.FRIENDS);
    }

    public void openGitHubPage() {
        Uri uri = Uri.parse("https://github.com/IRA-Team/SixHandshakes");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        getActivity().startActivity(intent);
    }

    @Override
    public void onPathFound(int[] ids) {
        int size = ids.length;
        switch (size) {
            case 3:
                loadUserById(ids[1], first);
                break;
            case 4:
                loadUserById(ids[1], first);
                loadUserById(ids[2], second);
                break;
            case 5:
                loadUserById(ids[1], first);
                loadUserById(ids[2], second);
                loadUserById(ids[3], third);
                break;
        }
        isProcessing.set(false);
    }

    @Override
    public void onNothingFound() {
        isProcessing.set(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                clear();
                //FIXME: rework to class method
                VKSdk.logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                /*
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setTitle(getResources().getString(R.string.logout));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

                isLoggedIn.set(true);
                loadUserById(0, self);
            }

            @Override
            public void onError(VKError error) {
            }
        });

        if (requestCode == SEARCH_REQUEST_CODE) {
            target.set(data.getParcelableExtra("user"));
        }
    }

    public static void loadUserById(int id, ObservableField<VKApiUser> target) {
        VKParameters params = VKParameters.from(
                VKApiConst.FIELDS, "photo_200");
        if (id != 0) params.put(VKApiConst.USER_ID, id);

        VKApi.users().get(params).executeWithListener(new SimpleCallback(response -> {
            VKApiUser user = new VKApiUser(response.json.getJSONArray("response").getJSONObject(0));
            target.set(user);
        }));
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        requestService = ((RequestService.RequestServiceBinder) service).getService();
        requestService.setListener(this);
        requestService.start(self.get().id, target.get().id);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}

package com.irateam.sixhandshakes.viewmodel;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.ObservableField;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;

import com.irateam.sixhandshakes.R;
import com.irateam.sixhandshakes.activity.MainActivity;
import com.irateam.sixhandshakes.activity.SearchActivity;
import com.irateam.sixhandshakes.binding.ActivityViewModel;
import com.irateam.sixhandshakes.databinding.ActivityMainBinding;
import com.irateam.sixhandshakes.service.RequestService;
import com.irateam.sixhandshakes.utils.SimpleCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.model.VKApiUser;

import static com.irateam.sixhandshakes.viewmodel.MainActivityVM.State.FINISHED;
import static com.irateam.sixhandshakes.viewmodel.MainActivityVM.State.NONE;
import static com.irateam.sixhandshakes.viewmodel.MainActivityVM.State.PROCESSING;
import static com.irateam.sixhandshakes.viewmodel.MainActivityVM.State.READY;

public class MainActivityVM extends ActivityViewModel<MainActivity, ActivityMainBinding>
        implements RequestService.ResultListener, ServiceConnection {

    private final static int SEARCH_REQUEST_CODE = 228;

    private RequestService requestService;

    public ObservableField<State> state = new ObservableField<>(NONE);

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

        loadUserById(0, self);
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
        state.set(FINISHED);
    }

    @Override
    public void onNothingFound() {
        state.set(FINISHED);
    }

    @Override
    public boolean onCreateOptionMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_logout:
                logout();
                return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEARCH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            target.set(data.getParcelableExtra("user"));
            state.set(READY);
        }
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

    private void logout() {
        VKSdk.logout();
    }

    public void startProcessing() {
        getActivity().startService(new Intent(getActivity(), RequestService.class));
        getActivity().bindService(new Intent(getActivity(), RequestService.class), this, 0);
        state.set(PROCESSING);
    }

    public void stopProcessing() {
    }

    public void clear() {
        state.set(NONE);
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

    public void onFabClick() {
        switch (state.get()) {
            case READY:
                startProcessing();
                break;

            case PROCESSING:
                stopProcessing();
                break;

            case FINISHED:
                clear();
                break;
        }
    }


    public static void loadUserById(int id, ObservableField<VKApiUser> target) {
        VKParameters params = VKParameters.from(VKApiConst.FIELDS, "photo_200");
        if (id != 0) {
            params.put(VKApiConst.USER_ID, id);
        }

        VKApi.users().get(params).executeWithListener(new SimpleCallback(response -> {
            VKApiUser user = new VKApiUser(response.json.getJSONArray("response").getJSONObject(0));
            target.set(user);
        }));
    }

    public enum State {
        NONE, READY, PROCESSING, FINISHED
    }

}

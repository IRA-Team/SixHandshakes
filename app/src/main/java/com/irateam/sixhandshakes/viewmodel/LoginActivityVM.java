package com.irateam.sixhandshakes.viewmodel;

import android.content.Intent;
import android.net.Uri;

import com.irateam.sixhandshakes.activity.LoginActivity;
import com.irateam.sixhandshakes.activity.MainActivity;
import com.irateam.sixhandshakes.binding.ActivityViewModel;
import com.irateam.sixhandshakes.databinding.ActivityLoginBinding;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class LoginActivityVM extends ActivityViewModel<LoginActivity, ActivityLoginBinding> {

    @Override
    public void initialize(LoginActivity activity, ActivityLoginBinding binding) {
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setElevation(0);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKSdk.onActivityResult(requestCode, resultCode, data, new VKAccessTokenCallback());
    }

    private class VKAccessTokenCallback implements VKCallback<VKAccessToken> {

        @Override
        public void onResult(VKAccessToken res) {
            getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }

        @Override
        public void onError(VKError error) {

        }
    }
}

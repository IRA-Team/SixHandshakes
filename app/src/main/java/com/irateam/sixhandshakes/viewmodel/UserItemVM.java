package com.irateam.sixhandshakes.viewmodel;

import android.databinding.ObservableInt;

import com.irateam.sixhandshakes.binding.ObservableString;
import com.vk.sdk.api.model.VKApiUserFull;

public class UserItemVM {

    public final ObservableInt id = new ObservableInt();
    public final ObservableString fullName = new ObservableString();
    public final ObservableString photoURL = new ObservableString();

    public UserItemVM(VKApiUserFull user) {
        id.set(user.id);
        fullName.set(user.first_name + " " + user.screen_name);
        photoURL.set(user.photo_100);
    }
}

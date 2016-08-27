package com.irateam.sixhandshakes.ui.viewholder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.irateam.sixhandshakes.databinding.ItemUserBinding;
import com.irateam.sixhandshakes.viewmodel.UserItemVM;
import com.vk.sdk.api.model.VKApiUserFull;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private final ItemUserBinding binding;

    public UserViewHolder(View v) {
        super(v);
        setIsRecyclable(false);
        binding = DataBindingUtil.bind(v);
    }

    public void setUser(VKApiUserFull user) {
        binding.setViewModel(new UserItemVM(user));
    }
}

package com.irateam.sixhandshakes.activity;

import com.irateam.sixhandshakes.R;
import com.irateam.sixhandshakes.binding.BindingActivity;
import com.irateam.sixhandshakes.viewmodel.LoginActivityVM;

public class LoginActivity extends BindingActivity<LoginActivityVM> {

    @Override
    public int onCreateBinding() {
        return R.layout.activity_login;
    }

    @Override
    public LoginActivityVM onCreateViewModel() {
        return new LoginActivityVM();
    }

}

package com.irateam.sixhandshakes.activity;

import com.irateam.sixhandshakes.R;
import com.irateam.sixhandshakes.binding.BindingActivity;
import com.irateam.sixhandshakes.viewmodel.MainActivityVM;

public class MainActivity extends BindingActivity<MainActivityVM> {

    @Override
    public int onCreateBinding() {
        return R.layout.activity_main;
    }

    @Override
    public MainActivityVM onCreateViewModel() {
        return new MainActivityVM();
    }

}

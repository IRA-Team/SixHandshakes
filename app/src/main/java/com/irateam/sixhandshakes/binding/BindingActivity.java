package com.irateam.sixhandshakes.binding;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.irateam.sixhandshakes.Application;
import com.irateam.sixhandshakes.BR;

public abstract class BindingActivity<VM extends ActivityViewModel> extends AppCompatActivity {

    protected VM viewModel;

    @LayoutRes
    public abstract int onCreateBinding();

    public abstract VM onCreateViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding binding = DataBindingUtil.setContentView(this, onCreateBinding());
        viewModel = onCreateViewModel();
        viewModel.setUp(this, binding);
        binding.setVariable(BR.viewModel, viewModel);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        return viewModel.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewModel.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Application getApplicationContext() {
        return (Application) super.getApplicationContext();
    }
}

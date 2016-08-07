package com.irateam.sixhandshakes.binding;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.view.Menu;
import android.view.MenuItem;

public abstract class ActivityViewModel<A extends BindingActivity, B extends ViewDataBinding> {

    private A activity;
    private B binding;

    public A getActivity() {
        return activity;
    }

    public B getBinding() {
        return binding;
    }

    /**
     * @param activity
     * @param binding
     */
    public final void setUp(A activity, B binding) {
        this.activity = activity;
        this.binding = binding;
        initialize(activity, binding);
    }

    public void initialize(A activity, B binding) {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public final void setActivityResultAndFinish(Intent intent) {
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
    }

    public boolean onCreateOptionMenu(Menu menu) {
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }


    public void onBackPressed() {

    }

    public void onResume() {

    }

    public void onPause() {

    }
}

package com.irateam.sixhandshakes.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.irateam.sixhandshakes.R;

public class LoadingStub extends FrameLayout {

    private CustomProgressBar progressBar;

    public LoadingStub(Context context) {
        super(context);
    }

    public LoadingStub(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LoadingStub(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingStub(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_loading, this);
        progressBar = (CustomProgressBar) findViewById(R.id.progress_bar);
    }

}

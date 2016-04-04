package com.irateam.sixhandshakes.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.irateam.sixhandshakes.R;

public class CustomProgressBar extends View {
    Movie movie;

    public CustomProgressBar(Context context) {
        super(context);
        init();
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @SuppressWarnings("ResourceType")
    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        movie = Movie.decodeStream(getContext().getResources().openRawResource(R.drawable.load));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (movie != null) {
            movie.setTime(
                    (int) SystemClock.uptimeMillis() % movie.duration());
            movie.draw(canvas, 0, 0);
            invalidate();
        }
    }
}
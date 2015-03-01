package io.picarete.picarete.ui.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * Created by iem on 12/01/15.
 */
public class CustomFontGridLayout extends GridLayout {

    public boolean interceptEvent = false;

    public CustomFontGridLayout(Context context) {
        super(context);
    }

    public CustomFontGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFontGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return interceptEvent;
    }
}

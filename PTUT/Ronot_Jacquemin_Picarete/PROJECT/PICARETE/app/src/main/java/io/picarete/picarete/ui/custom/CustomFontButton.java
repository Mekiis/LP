package io.picarete.picarete.ui.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by iem on 12/01/15.
 */
public class CustomFontButton extends Button {

    public CustomFontButton(Context context) {
        super(context);
        initializeFont(context);
    }

    public CustomFontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeFont(context);
    }

    public CustomFontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeFont(context);
    }

    private void initializeFont(Context context){
        if(!isInEditMode()){
            Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "trebuchetms.ttf");
            setTypeface(myTypeface);
        }
    }
}

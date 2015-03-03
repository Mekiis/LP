package io.picarete.picarete.ui.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by iem on 12/01/15.
 */
public class CustomFontEditText extends EditText {

    public CustomFontEditText(Context context) {
        super(context);
        initializeFont(context);
    }

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeFont(context);
    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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

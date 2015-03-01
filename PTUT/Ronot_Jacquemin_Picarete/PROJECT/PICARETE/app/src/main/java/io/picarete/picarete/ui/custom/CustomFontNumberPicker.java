package io.picarete.picarete.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by iem on 12/01/15.
 */
public class CustomFontNumberPicker extends NumberPicker{
    private Context mContext;

    public CustomFontNumberPicker(Context context) {
        super(context);
        this.mContext = context;
    }

    public CustomFontNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
   }

    public CustomFontNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
   }

    /*
    public boolean setNumberPickerTextFont(Context context)
    {
        if(!isInEditMode()){
            Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "trebuchetms.ttf");

            final int count = getChildCount();
            for(int i = 0; i < count; i++){
                getChildAt(i) = new CustomFontEditText(context);
            }
        }
        return false;
    }*/

}

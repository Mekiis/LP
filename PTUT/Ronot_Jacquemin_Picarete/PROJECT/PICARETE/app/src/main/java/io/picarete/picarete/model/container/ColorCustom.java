package io.picarete.picarete.model.container;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by root on 1/9/15.
 */
public class ColorCustom implements Serializable {
    int number = -1;
    String str = "";

    public ColorCustom(int number){
        this.number = number;
    }

    public ColorCustom(String str){
        this.str = str;
    }

    public ColorCustom(){

    }

    public int getColor(){
        int c = Color.rgb(0, 0, 0);

        if(number != -1){
            c = number;
        } else if(str.compareToIgnoreCase("") != 0){
            c = Color.parseColor(str);
        }

        return c;
    }
}

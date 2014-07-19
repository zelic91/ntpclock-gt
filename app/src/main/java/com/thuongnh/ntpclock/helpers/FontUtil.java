package com.thuongnh.ntpclock.helpers;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by zelic on 7/19/14.
 * This class is helper class to load custom font
 */
public class FontUtil {

    //Log tag
    private static final String TAG = FontUtil.class.getSimpleName();

    //Font name
    public static final String LIGHT = "light.otf";
    public static final String BOLD = "bold.ttf";
    public static final String REGULAR = "regular.ttf";


    /**
     * Return typeface
     * @param context
     * @param fontName
     * @return
     */
    public static Typeface getFont(Context context, String fontName) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/"+fontName);
    }

}

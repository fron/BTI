package com.example.francisqueins.breakingtheice;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TypefacedTextView extends android.support.v7.widget.AppCompatTextView {

    public TypefacedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "Lato-Light.ttf");
        setTypeface(typeface);
    }
}

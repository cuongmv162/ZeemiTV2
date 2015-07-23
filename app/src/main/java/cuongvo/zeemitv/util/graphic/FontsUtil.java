package cuongvo.zeemitv.util.graphic;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Hashtable;

public final class FontsUtil {
    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    public static Typeface get(Context c) {
        String name = "roboto_bold";
        synchronized (cache) {
            if (!cache.containsKey(name)) {
                Typeface t = Typeface.createFromAsset(c.getAssets(), String.format("fonts/%s.ttf", name));
                cache.put(name, t);
            }
            return cache.get(name);
        }
    }
    public static Typeface getReg(Context c) {
        String name = "roboto_regular";
        synchronized (cache) {
            if (!cache.containsKey(name)) {
                Typeface t = Typeface.createFromAsset(c.getAssets(), String.format("fonts/%s.ttf", name));
                cache.put(name, t);
            }
            return cache.get(name);
        }

    }
    public static void setViewGroupTypeface(ViewGroup container,Context c) {
        final int children = container.getChildCount();
        Typeface typefaceBold = get(c);
        Typeface typefaceLight = getReg(c);
        for (int i = 0; i < children; i++) {
            View child = container.getChildAt(i);

            if (child instanceof TextView) {
                if(((TextView) child).getTypeface() != null) {
                    if (((TextView) child).getTypeface().getStyle() == Typeface.BOLD)
                        setTextViewTypeface((TextView) child, typefaceBold);
                    else
                        setTextViewTypeface((TextView) child, typefaceLight);
                } else
                    setTextViewTypeface((TextView) child, typefaceLight);
            } else if (child instanceof ViewGroup) {
                setViewGroupTypeface((ViewGroup) child,c);
            }
        }
    }

    public static void setTextViewTypeface(TextView textView, Typeface typeface) {
        textView.setTypeface(typeface);
    }
}

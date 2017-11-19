package cn.shorr.slidebar;

import android.content.Context;
import android.util.TypedValue;

/**
 * Density Unit Convert
 * Created by shorr on 2017/11/19.
 */

public class UnitConvert {

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }


    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }
}

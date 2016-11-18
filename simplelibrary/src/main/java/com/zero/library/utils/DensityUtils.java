package com.zero.library.utils;

import android.content.Context;
import android.util.TypedValue;

import java.math.BigDecimal;

/**
 * 作者： Wang
 * 时间： 2016/11/18
 */

public class DensityUtils {

    /**
     * DP 转 PX
     *
     * @param context Context
     * @param dpVal   dpVal
     * @return float
     */
    public static float dp2px(Context context, float dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * SP 转 PX
     *
     * @param context Context
     * @param dpVal   dpVal
     * @return float
     */
    public static float sp2px(Context context, float dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dpVal, context.getResources().getDisplayMetrics());
    }


    /**
     * PX 转 DP
     *
     * @param context Context
     * @param dpVal   dpVal
     * @return float
     */
    public static float px2dp(Context context, float dpVal) {
        float density = context.getResources().getDisplayMetrics().density;
        return BigDecimalUtils.divide(dpVal, density, 2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}

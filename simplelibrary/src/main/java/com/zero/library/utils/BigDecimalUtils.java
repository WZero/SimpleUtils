package com.zero.library.utils;

import java.math.BigDecimal;

/**
 * 作者： Wang
 * 时间： 2016/11/18
 */

public class BigDecimalUtils {
    /**
     * @param nub1  double
     * @param nub2  double
     * @param scale 计算保留长度
     * @return double
     */
    public static double divide(double nub1, double nub2, int scale) {
        return divide(nub1, nub2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * @param nub1         double
     * @param nub2         double
     * @param scale        计算保留长度
     * @param roundingMode 保留模式 如:BigDecimal.ROUND_HALF_UP
     * @return double
     */
    public static BigDecimal divide(double nub1, double nub2, int scale, int roundingMode) {
        return new BigDecimal(nub1).divide(new BigDecimal(nub2), scale, roundingMode);
    }
}

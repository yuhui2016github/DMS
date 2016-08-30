package com.example.yuhui.dms;

import java.text.NumberFormat;

/**
 * Created by yuhui on 2016-8-10.
 */
public class Utils {
    /**
     * 不显示成科学计数法
     */
    public static String formatNumber(float value) {
        NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return nf.format(value);
    }

}

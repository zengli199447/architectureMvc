package com.nativeosproject.cn.mvcjobproject.livetools.controller;


import global.MyApplication;

public class CommonUtil {

    public static int dip2px(float dpValue) {
        float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

package cn.tianyu.kotlin_learn.section3;

import java.util.ArrayList;

import cn.tianyu.dailypractice.utils.LogUtil;

public class InvokeFromJava {
    public static final String TAG = "InvokeFromJava";

    public static void invokeKotlinMethod() {
        ArrayList collection = new ArrayList();
        collection.add(1);
        collection.add(2);
        collection.add(4);
        LogUtil.INSTANCE.d(TAG, StringFuctions.joinToString(collection, ", ", "", ""));
    }
}

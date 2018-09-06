package cn.tianyu.kotlin_learn;

import java.io.File;
import java.util.ArrayList;

import cn.tianyu.dailypractice.utils.LogUtil;
import cn.tianyu.kotlin_learn.section3.StringFuctions;
import cn.tianyu.kotlin_learn.section4.CaseInsensitiveFileComparator;
import cn.tianyu.kotlin_learn.section4.User2;

public class InvokeFromJava {
    public static final String TAG = "InvokeFromJava";

    public static void invokeKotlinMethod() {
        ArrayList collection = new ArrayList();
        collection.add(1);
        collection.add(2);
        collection.add(4);
        LogUtil.INSTANCE.d(TAG, StringFuctions.joinToString(collection, ", ", "", ""));
        CaseInsensitiveFileComparator.INSTANCE.compare(new File("/z"), new File("/a"));
        User2.Loader.newFacebookUser(2);
    }
}

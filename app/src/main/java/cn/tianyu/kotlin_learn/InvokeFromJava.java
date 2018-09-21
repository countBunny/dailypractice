package cn.tianyu.kotlin_learn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.tianyu.dailypractice.utils.LogUtil;
import cn.tianyu.kotlin_learn.section3.StringFuctions;
import cn.tianyu.kotlin_learn.section4.CaseInsensitiveFileComparator;
import cn.tianyu.kotlin_learn.section4.User2;
import cn.tianyu.kotlin_learn.section8.LambdaPremierKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

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
        //invoke lambda
        LambdaPremierKt.twoAndThree(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer invoke(Integer a, Integer b) {
                System.out.println("number a=" + a + " number b=" + b);
                return a + b;
            }
        });
        List<String> strings = new ArrayList<>();
        strings.add("42");
        CollectionsKt.forEach(strings, new Function1<String, Unit>() {
            @Override
            public Unit invoke(String s) {
                System.out.println(s);
                return Unit.INSTANCE;
            }
        });
    }

    /**
     * try with resource 语句
     * @param path
     * @return
     * @throws IOException
     */
    static String readFirstLineFromFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }
}

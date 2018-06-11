package cn.tianyu.dailypractice.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import cn.tianyu.dailypractice.R;
import cn.tianyu.dailypractice.ui.activity.App;

public class WhiteListUtils {

    private static final String SKIP_WHITELIST_APP = "SKIP_WHITELIST_APP";
    private static final String TAG = "WhiteListUtils";

    private static final String XIAOMI = "xiaomi";
    private static final String HUAWEI = "huawei";
    private static final String OPPO = "oppo";
    private static final String VIVO = "vivo";

    private static SharedPreferences settings;

    private WhiteListUtils() {
    }

    public static void showWhiteListingApps(Context context) {
        if (settings == null)
            settings = context.getSharedPreferences("WhiteListUtils", Context.MODE_PRIVATE);
        if (!settings.getBoolean(SKIP_WHITELIST_APP, false))
            checkOSCompat(context);
    }

    private static void checkOSCompat(Context context) {
        try {
            Intent intent = new Intent();
            String manufacturer = android.os.Build.MANUFACTURER;
            if (XIAOMI.equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            } else if (OPPO.equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
                intent.setComponent(new ComponentName("com.coloros.oppoguardelf", "com.coloros.powermanager.fuelgaue.PowerConsumptionActivity"));
            } else if (VIVO.equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
            } else if (HUAWEI.equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
            }

            if (isCallable(context, intent)) {
                showAlertWindow(context, intent);
            } else {
//                if (BuildConfig.BUILD_TYPE.contains("release"))
//                    Crashlytics.log("Intent not callable for whitelisting " + intent.toString());
                LogUtil.INSTANCE.e(TAG, "Intent not callable for whitelisting " + intent.toString());
            }
        } catch (Exception e) {
//            if (BuildConfig.BUILD_TYPE.contains("release")) {
//                Crashlytics.logException(e);
//            }
            LogUtil.INSTANCE.e(TAG, "checkOSCompat Error " + e.getMessage());
        }
    }

    private static void showAlertWindow(final Context context, final Intent intent) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Protected Apps")
                .setMessage(String.format("%s requires to be enabled in 'Protected Apps' to function properly.%n", context.getString(R.string.app_name)))
                .setPositiveButton("Go to Apps", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(intent);
                        settings.edit().putBoolean(SKIP_WHITELIST_APP, true).apply();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }


    private static boolean isCallable(Context context, Intent intent) {
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static void hasProtectedApps(String packaGe, String activity) {
        try {
            String replacedActivityName = activity.replace(packaGe, "");
            //String cmd = "am start -n com.huawei.systemmanager/.optimize.process.ProtectActivity";
            String cmd = "am start -n " + packaGe + "/" + replacedActivityName;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                cmd += " --user " + getUserSerial();
            }
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
//            if (BuildConfig.BUILD_TYPE.contains("release"))
//                Crashlytics.logException(e);
            LogUtil.INSTANCE.e(TAG, "isProtectedApps Error " + e.getMessage());

        }
    }

    private static String getUserSerial() {
        //noinspection ResourceType
        Object userManager = App.Companion.getInstance().getSystemService(Context.USER_SERVICE);
        if (null == userManager) return "";

        try {
            Method myUserHandleMethod = android.os.Process.class.getMethod("myUserHandle", (Class<?>[]) null);
            Object myUserHandle = myUserHandleMethod.invoke(android.os.Process.class, (Object[]) null);
            Method getSerialNumberForUser = userManager.getClass().getMethod("getSerialNumberForUser", myUserHandle.getClass());
            Long userSerial = (Long) getSerialNumberForUser.invoke(userManager, myUserHandle);
            if (userSerial != null) {
                return String.valueOf(userSerial);
            } else {
                return "";
            }
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
//            if (BuildConfig.BUILD_TYPE.contains("release"))
//                Crashlytics.logException(e);
            LogUtil.INSTANCE.e(TAG, "getUserSerial Error " + e.getMessage());
        }
        return "";
    }
}

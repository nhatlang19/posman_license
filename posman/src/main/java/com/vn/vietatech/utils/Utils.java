package com.vn.vietatech.utils;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Utils {
    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo() != null;
    }

    public static String getSerialNumber(ContentResolver contentResolver) {
        String serialNumber = Build.SERIAL != Build.UNKNOWN ? Build.SERIAL : Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);

        return serialNumber.substring(serialNumber.length() - 6);
    }

    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();

        return deviceId.substring(deviceId.length() - 6);
    }

    public static void showAlert(Context context, String message) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        // Create the AlertDialog object and return it
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static int parseColor(String colorDelphi) {
        int RGB_Delphi = Integer.parseInt(colorDelphi);

        int R = (int) RGB_Delphi % 256;
        int G = ((int) RGB_Delphi / 256) % 256;
        int B = ((int) RGB_Delphi / 256 / 256) % 256;

        return Color.rgb(R, G, B);
    }

    public static String formatPrice(int price) {
        Locale locale = new Locale("en", "UK");
        String pattern = "###,###,###";

        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern(pattern);

        return decimalFormat.format(price);
    }

    public static int parseStringToInt(String s) {
        s = s.replaceAll(",", ""); //remove commas
        return (int) Math.round(Double.parseDouble(s)); //return rounded double cast to int
    }

    /**
     * This snippet allows UI on main thread. Normally it's 2 lines but since
     * we're supporting 2.x, we need to reflect.
     */
    public static void disableStrictMode() {
        // StrictMode.ThreadPolicy policy = new
        // StrictMode.ThreadPolicy.Builder().permitAll().build();
        // StrictMode.setThreadPolicy(policy);

        try {
            Class<?> strictModeClass = Class.forName("android.os.StrictMode",
                    true, Thread.currentThread().getContextClassLoader());
            Class<?> threadPolicyClass = Class.forName(
                    "android.os.StrictMode$ThreadPolicy", true, Thread
                            .currentThread().getContextClassLoader());
            Class<?> threadPolicyBuilderClass = Class.forName(
                    "android.os.StrictMode$ThreadPolicy$Builder", true, Thread
                            .currentThread().getContextClassLoader());

            Method setThreadPolicyMethod = strictModeClass.getMethod(
                    "setThreadPolicy", threadPolicyClass);

            Method detectAllMethod = threadPolicyBuilderClass
                    .getMethod("detectAll");
            Method penaltyMethod = threadPolicyBuilderClass
                    .getMethod("penaltyLog");
            Method buildMethod = threadPolicyBuilderClass.getMethod("build");

            Constructor<?> threadPolicyBuilderConstructor = threadPolicyBuilderClass
                    .getConstructor();
            Object threadPolicyBuilderObject = threadPolicyBuilderConstructor
                    .newInstance();

            Object obj = detectAllMethod.invoke(threadPolicyBuilderObject);

            obj = penaltyMethod.invoke(obj);
            Object threadPolicyObject = buildMethod.invoke(obj);
            setThreadPolicyMethod.invoke(strictModeClass, threadPolicyObject);
        } catch (Exception ex) {
            Log.w("disableStrictMode", ex);
        }
    }

    public static final String implode(String glue, String[] items) {
        StringBuilder sb = new StringBuilder();
        for (String item : items) {
            sb.append(item);
            sb.append(glue);
        }
        return sb.delete(sb.length() - glue.length(), sb.length()).toString();
    }

    public static String getCurrentDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String POSBizDate = sdf.format(new Date());
        return POSBizDate;
    }

    public static String getRandomString(int length)
    {
        String randomStr = UUID.randomUUID().toString();
        while(randomStr.length() < length) {
            randomStr += UUID.randomUUID().toString();
        }
        return randomStr.substring(0, length).toUpperCase();
    }
}

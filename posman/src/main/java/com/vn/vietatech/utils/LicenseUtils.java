package com.vn.vietatech.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.vn.vietatech.posman.R;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class LicenseUtils {
    private static LicenseUtils instance = null;
    private static String FILENAME = "LicenseKey";
    private static String FILENAME_SETUP = "Setup";
    private final int salt = 23102014;
    Context context;

    protected LicenseUtils(Context context) {
        // Exists only to defeat instantiation.
        this.context = context;
    }

    public static LicenseUtils getInstance(Context context) {
        if (instance == null) {
            instance = new LicenseUtils(context);
        }
        return instance;
    }

    public boolean checkLicenseKey(String licenseKey) {
        String imei = Utils.getIMEI(context);
        String lc = createLicenseKey(imei);

        return licenseKey.equals(lc);
    }

    public String createLicenseKey(String imei) {
        int decimal = Integer.valueOf(imei);
        return decimal + salt + "";
    }

    public String decodeLicenseKey(String licenseKey) {
        int decimal = Integer.valueOf(licenseKey) - salt;

        return String.valueOf(decimal);
    }

    @SuppressLint("NewApi")
    public void write(String licenseKey)
            throws IOException {
        File dir = new File(context.getFilesDir().getPath() + "/"
                + context.getResources().getString(R.string.app_folder));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, FILENAME);
        Properties props = new Properties();
        props.setProperty("licenseKey", licenseKey);

        FileWriter writer = new FileWriter(file);
        props.store(writer, "licenseKey");
        writer.close();
    }

    @SuppressLint("NewApi")
    public String read() throws IOException {

        File file = new File(context.getFilesDir().getPath() + "/"
                + context.getResources().getString(R.string.app_folder) + "/"
                + FILENAME);
        if (file.exists() && file.isFile()) {
            FileReader reader = new FileReader(file);
            Properties props = new Properties();
            props.load(reader);

            return props.getProperty("licenseKey");
        }
        return null;
    }

    @SuppressLint("NewApi")
    public void writeSetupFirst()
            throws IOException {
        File dir = new File(context.getFilesDir().getPath() + "/"
                + context.getResources().getString(R.string.app_folder));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, FILENAME_SETUP);
        Properties props = new Properties();
        props.setProperty("isSetupped", "1");

        FileWriter writer = new FileWriter(file);
        props.store(writer, "isSetupped");
        writer.close();
    }

    @SuppressLint("NewApi")
    public String readSetupFirst() throws IOException {
        File file = new File(context.getFilesDir().getPath() + "/"
                + context.getResources().getString(R.string.app_folder) + "/"
                + FILENAME_SETUP);
        if (file.exists() && file.isFile()) {
            FileReader reader = new FileReader(file);
            Properties props = new Properties();
            props.load(reader);

            return props.getProperty("isSetupped");
        }
        return null;
    }


}

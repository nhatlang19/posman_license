package com.vn.vietatech.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.vn.vietatech.model.Cashier;
import com.vn.vietatech.posman.R;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class UserUtil {
    private static String FILENAME = "NearUser";

    @SuppressLint("NewApi")
    public static void write(Cashier cashier, Context context)
            throws IOException {
        File dir = new File(context.getFilesDir().getPath() + "/"
                + context.getResources().getString(R.string.app_folder));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, FILENAME);
        Properties props = new Properties();
        props.setProperty("userId", cashier.getId());

        FileWriter writer = new FileWriter(file);
        props.store(writer, "config");
        writer.close();
    }

    @SuppressLint("NewApi")
    public static Cashier read(Context context) throws IOException {

        File file = new File(context.getFilesDir().getPath() + "/"
                + context.getResources().getString(R.string.app_folder) + "/"
                + FILENAME);
        if (file.exists() && file.isFile()) {
            FileReader reader = new FileReader(file);
            Properties props = new Properties();
            props.load(reader);

            Cashier casher = new Cashier();
            casher.setId(props.getProperty("userId"));
            reader.close();
            return casher;
        }
        return null;
    }
}

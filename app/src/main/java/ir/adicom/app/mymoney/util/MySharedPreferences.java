package ir.adicom.app.mymoney.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 *
 * Created by Y.P on 29/08/2018.
 */

public class MySharedPreferences {

    public static void savePreferences(Context mContext, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value).apply();
    }

    public static String getPreferences(Context context, String keyValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(keyValue, "");
    }

    public static void removeAllSharedPreferences(Context mContext) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }

}

package org.pindad.jemuran.util.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import org.pindad.jemuran.R;

public class SaveSharedPreference {
    static SharedPreferences getSharedPreferences(Context ctx) {
        return ctx.getSharedPreferences(ctx.getString(R.string.preference_file_key),ctx.MODE_PRIVATE);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(ctx.getString(R.string.username), userName);
        editor.commit();
    }
    public static void setLongtitude(Context ctx, String longtitude)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(ctx.getString(R.string.longtitude), longtitude);
        editor.commit();
    }
    public static void setLatitude(Context ctx, String latitude)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(ctx.getString(R.string.latitude), latitude);
        editor.commit();
    }
    public static void deletePreference(Context ctx){
        getSharedPreferences(ctx).edit().clear().commit();
    }
    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(ctx.getString(R.string.username), null);
    }
    public static String getLongtitude(Context ctx)
    {
        return getSharedPreferences(ctx).getString(ctx.getString(R.string.longtitude), null);
    }
    public static String getLatitude(Context ctx)
    {
        return getSharedPreferences(ctx).getString(ctx.getString(R.string.latitude), null);
    }
}

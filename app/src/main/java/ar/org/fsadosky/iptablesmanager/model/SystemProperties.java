package ar.org.fsadosky.iptablesmanager.model;

import java.lang.reflect.Method;
import android.util.Log;
public class SystemProperties {

    public static String get(String key) {
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            if (clazz != null) {
                Method method = clazz.getDeclaredMethod("get", String.class);
                if (method != null) {
                    String prop = (String)method.invoke(null, key);
                    Log.e("WHOOPS", "invoked method get");
                    return prop;
                } else {
                    Log.e("WHOOPS", "Cannot reflect method get on class android.os.SystemProperties");
                }
            } else {
                Log.e("WHOOPS", "Cannot reflect android.os.SystemProperties");
            }
        } catch (Exception e) {
            Log.e("WHOOPS", "Exception during reflection: " + e.getMessage());
        }
        return "";
    }
    public static void set(String key, String value) {
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            if (clazz != null) {
                Method method = clazz.getDeclaredMethod("set", String.class, String.class);
                if (method != null) {
                    method.invoke(null, key, value);
                    Log.e("WHOOPS", "invoked method set");
                } else {
                    Log.e("WHOOPS", "Cannot reflect method get on class android.os.SystemProperties");
                }
            } else {
                Log.e("WHOOPS", "Cannot reflect android.os.SystemProperties");
            }
        } catch (Exception e) {
            Log.e("WHOOPS", "Exception during reflection: " + e.getMessage());
        }
    }
}
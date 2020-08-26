package eslam.emad.sofra.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesManger {

    private SharedPreferences sharedPreferences = null;
    private static SharedPreferencesManger INSTANCE = null;

    private SharedPreferencesManger(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(
                    "SofraSharedPreferences", MODE_PRIVATE);
        }
    }

    public static SharedPreferencesManger getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPreferencesManger(context);
        }
        return INSTANCE;
    }

    public void setStringValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setIntegerValue(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void setBooleanValue(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        return sharedPreferences.getString(key,"Empty");
    }

    public int getIntegerValue(String key) {
        return sharedPreferences.getInt(key,0);
    }

    public boolean getBooleanValue(String key) {
        return sharedPreferences.getBoolean(key,false);
    }
}

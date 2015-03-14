package mobi.braincode.pushegro.client.repository;

import android.content.Context;
import android.content.SharedPreferences;
import mobi.braincode.pushegro.client.LoginActivity;

public class SharedPreferencesFacade {

    public static String getString(Context context, String propertyRegId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        return prefs.getString(propertyRegId, "");
    }

    public static int getIntValue(Context context, String propertyName) {
        return getGCMPreferences(context).getInt(propertyName, Integer.MIN_VALUE);
    }

    public static SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the registration ID in your app is up to you.
        return context.getSharedPreferences(LoginActivity.class.getSimpleName(), Context.MODE_PRIVATE);
    }

    public static void saveString(Context context, String propertyName, String propertyValue) {
        final SharedPreferences prefs = SharedPreferencesFacade.getGCMPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(propertyName, propertyValue);
        editor.commit();
    }
}

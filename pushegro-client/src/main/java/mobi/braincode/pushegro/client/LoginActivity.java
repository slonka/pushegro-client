package mobi.braincode.pushegro.client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import mobi.braincode.pushegro.client.gcm.GcmIntentService;
import mobi.braincode.pushegro.client.repository.SharedPreferencesFacade;
import mobi.braincode.pushegro.client.rest.RestFacade;

import java.io.IOException;

import static mobi.braincode.pushegro.client.repository.SharedPreferencesProperties.*;


public class LoginActivity extends Activity {

    public static final String TAG = "pushegro";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static String SENDER_ID = "149731509406";
    private GoogleCloudMessaging gcm;

    private Context context;
    private String registrationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        String predicatesToUpdate = intent.getStringExtra(GcmIntentService.SERVER_MESSAGE);

        context = getApplicationContext();

        Button loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView usernameView = (TextView) findViewById(R.id.loginUsernameTxt);
                final String username = usernameView.getText().toString();

                SharedPreferencesFacade.saveString(context, PROPERTY_USERNAME, username);
                new AsyncTask<Void, Void, Void>() {
                    ProgressDialog dialog;

                    @Override
                    protected void onPreExecute() {
                        dialog = ProgressDialog.show(LoginActivity.this, "Logowanie...", "", true, false);
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        Log.e(this.getClass().getSimpleName(), ">>>>>> Pre register");
                        RestFacade.register(username, registrationId);
                        Log.e(this.getClass().getSimpleName(), ">>>>>> Post register");
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        onCancelled();

                        Intent intent = new Intent(context, QueryListActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    protected void onCancelled() {
                        Log.e(this.getClass().getSimpleName(), ">>>>>> Cancelled");
                        if (dialog != null) dialog.dismiss();
                    }

                    @Override
                    protected void onCancelled(Void aVoid) {
                        onCancelled();
                    }
                }.execute();
            }
        });

        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            registrationId = getRegistrationId(context);

            if (registrationId.isEmpty() || registrationId.startsWith("Error")) {
                registerInBackground();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private String getRegistrationId(Context context) {
        String registrationId = SharedPreferencesFacade.getString(context, PROPERTY_REG_ID);
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing registration ID is not guaranteed to work with
        // the new app version.
        int registeredVersion = SharedPreferencesFacade.getIntValue(context, PROPERTY_APP_VERSION);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }


    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    registrationId = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + registrationId;
                    storeRegistrationId(context, registrationId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.i("Executed GCM register", msg);
            }
        }.execute(null, null, null);
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = SharedPreferencesFacade.getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
}

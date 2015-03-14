package mobi.braincode.pushegro.client.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import mobi.braincode.pushegro.client.LoginActivity;
import mobi.braincode.pushegro.client.QueryListActivity;
import mobi.braincode.pushegro.client.R;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GcmIntentService extends IntentService {

    public static final String SERVER_MESSAGE = "predicatesChanged";
    public static final String SERVER_PREDICATES_NAMES = "predicatesNamesChanged";
    private AtomicInteger notificationId = new AtomicInteger();

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        String predicatesChanged = extras.getCharSequence(SERVER_MESSAGE).toString();
        String predicatesNamesChanged = extras.getCharSequence(SERVER_PREDICATES_NAMES).toString();

        List<String> predicatesNames = Arrays.asList(predicatesNamesChanged.split(","));

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                handleGcmMessage("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                handleGcmMessage("Deleted messages on server: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                showNotification(getApplicationContext(), "Pushegro wykryło zmiany!", generateNotificationMessage(predicatesNames), predicatesChanged);
                handleGcmMessage(predicatesChanged);
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private String generateNotificationMessage(List<String> predicatesNames) {
        String result = "Zmiany w aukcjach:\n";
        result += "- " + predicatesNames.get(0);
        if (predicatesNames.size() > 1) {
            result += "\n- " + predicatesNames.get(1);
        } else {
            result += "\n" + "...";
        }

        return result;
    }

    private void handleGcmMessage(final String msg) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showNotification(Context context, String title, String text, String predicatesChanged) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon_clean)
                        .setContentTitle(title)
                        .setContentText(text);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, QueryListActivity.class);
        resultIntent.putExtra(GcmIntentService.SERVER_MESSAGE, predicatesChanged);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(LoginActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(notificationId.addAndGet(1), mBuilder.build());
    }
}

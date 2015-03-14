package mobi.braincode.pushegro.client.gcm;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import mobi.braincode.pushegro.client.LoginActivity;
import mobi.braincode.pushegro.client.R;

import java.util.concurrent.atomic.AtomicInteger;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    AtomicInteger notificationId = new AtomicInteger();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(this.getClass().getName(), "BROADCAST RECEIVER CALLED!");
        ComponentName comp = new ComponentName(context.getPackageName(), GcmIntentService.class.getName());
        startWakefulService(context, intent.setComponent(comp));
        showNotification(context, "Title", "notification from GCM");
        setResultCode(Activity.RESULT_OK);
    }

    public void showNotification(Context context, String title, String text) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon_clean)
                        .setContentTitle(title)
                        .setContentText(text);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, LoginActivity.class);

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

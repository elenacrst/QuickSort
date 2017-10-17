package com.example.elena.quicksort;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Elena on 10/10/2017.
 */

class NotificationUtils {
    private static Bitmap largeIcon(Context context){
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, R.drawable.done_sort);
    }

    private static PendingIntent contentIntent(Context context){
        Intent startMainActivity = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, 0, startMainActivity, PendingIntent.FLAG_UPDATE_CURRENT );
    }
    private static final int ACTION_DISMISS_PENDING_INTENT_ID = 1;
    private static final int ACTION_RESET_PENDING_INTENT_ID = 2;

    private static NotificationCompat.Action dismissAction(Context context){
        Intent dismissIntent = new Intent(context, ResetService.class);
        dismissIntent.setAction(ResetService.ACTION_DISMISS);
        PendingIntent dismissPendingIntent = PendingIntent.getService(context,ACTION_DISMISS_PENDING_INTENT_ID,
                dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action(R.drawable.ic_close_black_24dp,
                "Dismiss", dismissPendingIntent);
    }

    private static NotificationCompat.Action resetAction(Context context){
        Intent resetIntent = new Intent(context, ResetService.class);
        resetIntent.setAction(ResetService.ACTION_RESET);
        PendingIntent resetPendingIntent = PendingIntent.getService(context,ACTION_RESET_PENDING_INTENT_ID,
                resetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action(R.drawable.ic_refresh_black_24dp,
                "Reset Data", resetPendingIntent);
    }

    static void displayNotification(Context context){
        String text="Result: ";
        if (MainActivity.mSortedValues != null && MainActivity.mSortedValues.size()>0){
            text += MainActivity.mSortedValues.get(0);
            for (int i=1;i<MainActivity.mSortedValues.size(); i++){
                text+= ", "+MainActivity.mSortedValues.get(i);
            }
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setColor(context.getResources().getColor(R.color.colorAccent))
                .setLargeIcon(largeIcon(context))
                .setContentTitle("Quick sort complete")
                .setContentText("It's time to sort something new!")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setContentIntent(contentIntent(context))
                .addAction(resetAction(context))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .addAction(dismissAction(context))
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            builder.setPriority(Notification.PRIORITY_HIGH);
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    static void clearNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}

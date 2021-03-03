package NonActivityClasses;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.calocare.GiaoDienChinh;

public class AlarmReceiver extends BroadcastReceiver {
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    @Override
    public void onReceive(Context context, Intent intent) {
        pref = context.getSharedPreferences(AppControl.FOOD_PREF, Activity.MODE_PRIVATE);
        prefEditor = pref.edit();

        Bundle b = intent.getExtras();
        boolean isMidnight = b.getBoolean("isMidnight");

        if (isMidnight) {
            resetFood();
        } else if (!isMidnight) {
            createNoti(context);
        }
    }

    //https://stackoverflow.com/questions/52833540/push-notification-is-not-working-in-android-studio
    private void createNoti(Context context) {
        //Set the notification's tap action
        Intent intent = new Intent(context, GiaoDienChinh.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //Set notification title, content, defaults, and tapping noti action
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"default")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Insufficient")
                .setContentText("You need " + pref.getInt("foodRemain", 0) + " calories to complete today's goal")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Create a channel and set the importance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("com.example.calocare");

            NotificationChannel channel = new NotificationChannel(
                    "com.example.calocare",
                    "CaloCare",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        //Show the notification
        notificationManager.notify(0, builder.build());
    }

    private void resetFood() {
        prefEditor.putInt("foodAdded", 0);
        prefEditor.putInt("foodRemain", pref.getInt("foodGoal", 0) - 0);
        prefEditor.commit();
    }
}
package com.example.notifyassignment.receiver;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.example.notifyassignment.R;
import com.example.notifyassignment.ui.home.HomeActivity;
import com.example.notifyassignment.ui.home.ProfileActivity;

public class MyAlarmNotify extends BroadcastReceiver {

    private static final String CHANNEL_ID = "com.notificationTime.channelId";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyAlarmBel", "Alarm just fired");
        int importance = NotificationManager.IMPORTANCE_HIGH;
        Intent notificationIntent = new Intent(context, ProfileActivity.class);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(HomeActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.bell_old);
        PendingIntent pendingIntent = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        @SuppressLint("UseCompatLoadingForDrawables")
        Notification notification = builder
                .setContentTitle("Notify Alarm Notification")
                .setContentText("New Message for you, visit profile page..")
                .setTicker("New Alarm Alert!!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri, AudioManager.STREAM_VOICE_CALL )
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setStyle(new Notification.BigPictureStyle().bigPicture(icon))
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "NotificationDemo",importance);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notification);
    }
}

package com.thkang.svdr;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;

/**
 * Created by thkan on 2017-12-16.
 */

public class NotificationHelper extends ContextWrapper {
    private static final String SVDR_CHANNEL_ID = "com.thkang.svdr.SVDRDEV";
    private static final String SVDR_CHANNEL_NAME = "SVDRDEV Channel";

    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    public void createChannels(){
    NotificationChannel notificationChannel = new NotificationChannel(SVDR_CHANNEL_ID, SVDR_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
    notificationChannel.enableLights(true);
    notificationChannel.enableVibration(true);
    notificationChannel.setLightColor(Color.GREEN);
    notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

    getManager().createNotificationChannel(notificationChannel);
    }

    public NotificationManager getManager(){
        if(manager == null){
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public Notification.Builder getSVDRChannelNotification(String Big, String small){
        Intent notificationIntent = new Intent(this, NotificationSomething.class);
        notificationIntent.putExtra("menuFragment", "Police");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        return new Notification.Builder(getApplicationContext(), SVDR_CHANNEL_ID)
                .setContentText(Big)
                .setContentTitle(small)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.warning)
                .setAutoCancel(true);
    }
}

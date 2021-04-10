package com.kamaii.rider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kamaii.rider.interfacess.Consts;
import com.kamaii.rider.preferences.SharedPrefrence;
import com.kamaii.rider.service.MyService;
import com.kamaii.rider.ui.activity.BaseActivity;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    SharedPrefrence prefrence;
    int i = 0;
    Context context;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        prefrence = SharedPrefrence.getInstance(this);
        context = this;
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
        }


        if (remoteMessage.getData() != null) {
            if (remoteMessage.getData().containsKey(Consts.TYPE)) {
                if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.CHAT_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), Consts.CHAT_NOTIFICATION);
                } else if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.TICKET_COMMENT_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), Consts.TICKET_COMMENT_NOTIFICATION);
                } else if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.TICKET_STATUS_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), Consts.TICKET_STATUS_NOTIFICATION);
                } else if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.WALLET_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), Consts.WALLET_NOTIFICATION);
                } else if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.DECLINE_BOOKING_ARTIST_NOTIFICATION)) {
                    // sendNotification(getValue(remoteMessage.getData(), "body"), Consts.DECLINE_BOOKING_ARTIST_NOTIFICATION);
                    sendNotificationrringdecline(getValue(remoteMessage.getData(), "body"), Consts.DECLINE_BOOKING_ARTIST_NOTIFICATION);
                } else if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.START_BOOKING_ARTIST_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), Consts.START_BOOKING_ARTIST_NOTIFICATION);
                } else if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.RIDER_HAS_PICKUP_ORDER)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), Consts.RIDER_HAS_PICKUP_ORDER);
                } else if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.BRODCAST_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), Consts.BRODCAST_NOTIFICATION);
                } else if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.ADMIN_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), Consts.ADMIN_NOTIFICATION);
                } /*else if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.BOOK_ARTIST_NOTIFICATION)) {
                    sendNotificationrring(getValue(remoteMessage.getData(), "body"), Consts.BOOK_ARTIST_NOTIFICATION);
                }*/ else if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.JOB_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), Consts.JOB_NOTIFICATION);
                } else if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.DELETE_JOB_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), Consts.DELETE_JOB_NOTIFICATION);
                } else if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.SEND_RIDER_NOTIFICATION)) {
                    sendNotificationrring(getValue(remoteMessage.getData(), "body"), Consts.SEND_RIDER_NOTIFICATION);
                }/* else if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.ACCEPT_REQUEST_RIDER_NOTIFICATION)) {
                    sendNotificationrring(getValue(remoteMessage.getData(), "body"), Consts.ACCEPT_REQUEST_RIDER_NOTIFICATION);
                }*/ else if (remoteMessage.getData().get(Consts.TYPE).equalsIgnoreCase(Consts.VENDOR_HAS_BOOKING_END_SUCCESSFULLY)) {
                    sendNotificationstoprring(getValue(remoteMessage.getData(), "body"), Consts.VENDOR_HAS_BOOKING_END_SUCCESSFULLY);
                } else {
                    sendNotification(getValue(remoteMessage.getData(), "body"), "");
                }
            }

        }


    }

    public String getValue(Map<String, String> data, String key) {
        try {
            if (data.containsKey(key))
                return data.get(key);
            else
                return getString(R.string.app_name);
        } catch (Exception ex) {
            ex.printStackTrace();
            return getString(R.string.app_name);
        }
    }

    @Override
    public void onNewToken(String token) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Consts.DEVICE_TOKEN, token);
        editor.commit();

        Log.d(TAG, "Refreshed token: " + token);

    }

    private void notifyFragment(String json) {
        Intent intent = new Intent("nameOfTheActionShouldBeUniqueName");
        Bundle bundle = new Bundle();
        bundle.putString("json", json);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendNotification(String messageBody, String tag) {

        Log.e("NOTIFICATION_TRACKER","notification called 1");

        Intent intent = new Intent(this, BaseActivity.class);
        intent.putExtra(Consts.SCREEN_TAG, tag);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification);
        long[] v = {500, 1000};
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                //  .setSound(defaultSoundUri)
                //  .setVibrate(v)
                /*.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))*/
                .setContentText(messageBody).setAutoCancel(true).setContentIntent(pendingIntent);

        //  builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }


    private void sendNotificationstoprring(String messageBody, String tag) {

        Log.e("NOTIFICATION_TRACKER","notification called 2");
        Intent resultIntent = null;
        /*if(CURRENT_TAG.equalsIgnoreCase(TAG_BOOKINGS_cab)){
            resultIntent = new Intent(getApplicationContext(), BaseActivity.class);
            resultIntent.putExtra("Notification","notification");
            resultIntent.putExtra("message", messageBody);
            notifyFragment(messageBody);

        }else {
             resultIntent = new Intent(getApplicationContext(), BaseActivity.class);
            resultIntent.putExtra("Notification","notification");
            resultIntent.putExtra("message", messageBody);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(resultIntent);
        }*/

        resultIntent = new Intent(this, BaseActivity.class);
        resultIntent.putExtra("Notification", "notification");
        resultIntent.putExtra("message", messageBody);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(resultIntent);

        Intent intent = new Intent(context, MyService.class);
        context.stopService(intent);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))

                .setContentText(messageBody).setAutoCancel(true).setContentIntent(pendingIntent);


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());

    }


    private void sendNotificationrring(String messageBody, String tag) {

        Log.e("NOTIFICATION_TRACKER","notification called 3");

        try {

            Intent intent = new Intent(context, MyService.class);
            context.startService(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent resultIntent = null;
        /*if(CURRENT_TAG.equalsIgnoreCase(TAG_BOOKINGS_cab)){
            resultIntent = new Intent(getApplicationContext(), BaseActivity.class);
            resultIntent.putExtra("Notification","notification");
            resultIntent.putExtra("message", messageBody);
            notifyFragment(messageBody);

        }else {
             resultIntent = new Intent(getApplicationContext(), BaseActivity.class);
            resultIntent.putExtra("Notification","notification");
            resultIntent.putExtra("message", messageBody);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(resultIntent);
        }*/

        resultIntent = new Intent(this, BaseActivity.class);
        resultIntent.putExtra("Notification", "notification");
        resultIntent.putExtra("message", messageBody);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(resultIntent);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))

                .setContentText(messageBody).setAutoCancel(true).setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());

    }


    private void sendNotificationrringdecline(String messageBody, String tag) {

        Log.e("NOTIFICATION_TRACKER","notification called 4");

        try {

            Intent intent = new Intent(context, MyService.class);
            context.stopService(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Intent resultIntent = null;


        resultIntent = new Intent(context, BaseActivity.class);
        resultIntent.putExtra("Notification", "notification");
        resultIntent.putExtra("message", messageBody);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(resultIntent);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.cancel);

        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), soundUri);
        r.play();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                // .setSound(soundUri)//<--
                .setContentText(messageBody).setAutoCancel(true).setContentIntent(pendingIntent);


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());


    }


}


package com.example.rockit.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.rockit.Chat.Pag_message;
import com.example.rockit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

//https://www.youtube.com/watch?v=7Xc_5cduL-Y 10:30
public class MyFirebaseMessaging extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);


        //String sented = remoteMessage.getData().get("sented");
        //String user = remoteMessage.getData().get("user");
        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");


        NotificationCompat.Builder builder= new NotificationCompat.Builder(getApplicationContext(),"simple_notification");
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle(title);
        builder.setContentText(message);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0,builder.build());


        //TESTE
        //Log.d("MyFirebaseMesaging.c","sent Notification");
        //Log.d("MyFirebaseMesaging.c",user+"\n "+" "+title+" "+message);
        //notification_test(title, message);

    }

    public void notification_test(String title, String message){
        NotificationCompat.Builder builder= new NotificationCompat.Builder(getApplicationContext(),"simple_notification");
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle(title);
        builder.setContentText(message);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0,builder.build());
    }
}

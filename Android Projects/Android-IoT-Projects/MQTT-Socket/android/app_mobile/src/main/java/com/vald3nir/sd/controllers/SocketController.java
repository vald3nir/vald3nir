package com.vald3nir.sd.controllers;

import android.content.Intent;
import android.content.IntentFilter;

import com.vald3nir.sd.views.MainActivity;

public class SocketController {

    private SocketPublishTask socketPublishTask;
    private SubscriberReceiver subscriberReceiver;

    public void publish(String topicPublish, String message) {
        if (socketPublishTask != null) socketPublishTask.cancel(true);
        socketPublishTask = new SocketPublishTask(message, topicPublish);
        socketPublishTask.execute();
    }

    public void subscriber(MainActivity activity) {

        subscriberReceiver = new SubscriberReceiver(new SubscriberReceiver.ISubscriberReceiver() {
            @Override
            public void onSubscriber(String message) {
                activity.writeLog(message);
            }
        });

        activity.startService(new Intent(activity, SocketSubscriberService.class));

        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SubscriberReceiver.BROADCAST_ACTION);
            activity.registerReceiver(subscriberReceiver, intentFilter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stopSubscriber(MainActivity activity) {
        activity.unregisterReceiver(subscriberReceiver);
        activity.stopService(new Intent(activity, SocketSubscriberService.class));
    }
}

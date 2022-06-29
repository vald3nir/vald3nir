package com.vald3nir.sd.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class SubscriberReceiver extends BroadcastReceiver {

    public static final String BROADCAST_ACTION = "com.vald3nir.sd";
    public static final String DATA_PARAM = "data";
    private ISubscriberReceiver callback;

    public SubscriberReceiver(ISubscriberReceiver callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            callback.onSubscriber(intent.getStringExtra(DATA_PARAM));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    interface ISubscriberReceiver {
        void onSubscriber(String message);
    }


}

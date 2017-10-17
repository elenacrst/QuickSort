package com.example.elena.quicksort;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;

/**
 * Created by Elena on 10/10/2017.
 */

public class ResetService extends IntentService {

    public ResetService() {
        super("ResetService");
    }

    public static final String ACTION_DISMISS="com.example.elena.quicksort.action.DISMISS";
    public static final String ACTION_RESET = "com.example.elena.quicksort.action.RESET";
    Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION_DISMISS)){
            NotificationUtils.clearNotification(this);
        }else if (action.equals(ACTION_RESET)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MainActivity.resetData();
                }
            });
            NotificationUtils.clearNotification(this);
        }

    }
    private void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }
}


package org.linphone.receivers;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import android.app.Activity;
import android.content.ContextWrapper;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

import android.media.ToneGenerator;
import android.media.AudioManager;
import android.view.WindowManager;

import org.linphone.LinphonePreferences;
import org.linphone.LinphoneService;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    final public static String ONE_TIME = "onetime";
    final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
    @Override
    public void onReceive(Context context, Intent intent) {
        //getActivity(context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "gmobile:mywakelocktag");
        //Acquire the lock
        //wl.acquire();

        //You can do the processing here update the widget/remote views.
        Bundle extras = intent.getExtras();
        StringBuilder msgStr = new StringBuilder();
        tg.startTone(ToneGenerator.TONE_PROP_BEEP);
        /*
        if(extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)){
            msgStr.append("GMobile Timer : ");
            if (!LinphonePreferences.instance().isPushNotificationEnabled()) {
                //LinphoneService.instance().hideServiceNotification();
                Timer mTimer = new java.util.Timer();
                mTimer.schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                                //if (!LinphonePreferences.instance().isPushNotificationEnabled())
                                    //LinphoneService.instance().showServiceNotification();
                            }
                        },
                        500
                );
                //LinphoneService.instance().showServiceNotification();
                setOnetimeTimer(context);
            }
        }
        */
        Format formatter = new SimpleDateFormat("hh:mm:ss a");
        msgStr.append(formatter.format(new Date()));

        Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();

        //Release the lock
        //wl.release();
        //getActivity(context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static Activity getActivity(Context context) {
        if (context == null) return null;
        if (context instanceof Activity) return (Activity) context;
        if (context instanceof ContextWrapper) return getActivity(((ContextWrapper)context).getBaseContext());
        return null;
    }

    public void SetAlarm(Context context)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 30 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 30 , pi);
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
    public void setOnetimeTimer(Context context){
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 30, pi);
    }
}
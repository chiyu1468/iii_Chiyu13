package tw.org.iii.iii_chiyu13;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

//

public class Chiyu130Service extends Service {
    Timer timer;

    public Chiyu130Service() {
    }

    @Override
    public void onCreate() {
        Log.v("chiyu","Service onCreate");
        super.onCreate();
        timer = new Timer();
        timer.schedule(new ChiyuTask() ,1000,1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("chiyu","Service onStartCommand");

        if(intent != null) {
            boolean isStart = intent.getBooleanExtra("isStart1", false);
            if (isStart) {
                Log.v("chiyu", "First Start");
                i = intent.getIntExtra("value",-100);
            }

        }



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.v("chiyu","Service onDestroy");
        super.onDestroy();
        if(timer != null){
            timer.purge();
            timer.cancel();
            timer = null;
        }
    }

    private int i;
    private class ChiyuTask extends TimerTask{
        @Override
        public void run() {
            Log.v("chiyu"," i = " + i++);
            Intent it = new Intent("chiyuBroadcast");
            it.putExtra("data", " i = " + i);
            sendBroadcast(it);
        }
    }



    // 這是 2.3 版本以前的舊物
    @Override
    public void onStart(Intent intent, int startId) {
        //super.onStart(intent, startId);
        Log.v("chiyu","Service OLDonStart");
    }

    // 這裡還沒用到這個功能
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
}



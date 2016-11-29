package tw.org.iii.iii_chiyu13;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


// A. Service
//   1. "New" -> "Service" -> "Service"
//   2. Manifest 要有增加
//  -- Service 的生命週期 與 Activity 是分開的
//     所以就算 activity 關掉 再開  Service 也不會再跑 onCreate


public class MainActivity extends AppCompatActivity {
    private boolean isStrart;
    private MyRx chiyuRx;
    private TextView tv;

    public void StartService(View v){

        Intent it = new Intent(this, Chiyu130Service.class);
        // TO 給 Service
        it.putExtra("isStart1",isStrart);
        it.putExtra("value", (int)(Math.random()*100)+1);

        startService(it);


        isStrart = true;
    }

    public void StopService(View v){
        Intent it = new Intent(this, Chiyu130Service.class);
        stopService(it);
    }


    // ================================================


    // 要從 Service 收到 Broadcast
    // 需要一個 BroadcastReceiver 類別接收器
    private class MyRx extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("chiyu","Activity get");
            String msg = intent.getStringExtra("data");
            tv.setText(msg);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("chiyu","Activity onCreate");
        tv = (TextView) findViewById(R.id.tv);

        // 創造接收器物件實體
        chiyuRx = new MyRx();

        // 製作接收器的過濾器
        IntentFilter filter = new IntentFilter();
        filter.addAction("chiyuBroadcast");

        // 註冊(啟用) 接收器
        registerReceiver(chiyuRx,filter);
    }
    @Override
    public void finish() {
        Log.v("chiyu","Activity finish");
        // 記得程式結束 要把接收器 拆下來
        unregisterReceiver(chiyuRx);
        super.finish();
    }


    @Override
    protected void onStart() {
        Log.v("chiyu","Activity onStart");
        super.onStart();
    }
    @Override
    protected void onDestroy() {
        Log.v("chiyu","Activity onDestroy");
        super.onDestroy();
    }


}

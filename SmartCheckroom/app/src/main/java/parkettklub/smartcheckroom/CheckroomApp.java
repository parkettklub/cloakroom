package parkettklub.smartcheckroom;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.widget.Toast;

import com.esotericsoftware.minlog.Log;
import com.orm.SugarApp;
import com.orm.SugarDb;

import parkettklub.smartcheckroom.core.Core;

import static com.esotericsoftware.minlog.Log.LEVEL_DEBUG;

public class CheckroomApp extends SugarApp {

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SugarDb db = new SugarDb(this);
        Core.fillDataBase();

        Handler handler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                Bundle bb = msg.getData();
                String str = bb.getString("NetworkState");
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                Core.networkState = str;
            }
        };

        Core.findServer(getApplicationContext(), handler);

        /*
        String whoIAm = Core.whoAmI();

        Toast.makeText(getApplicationContext(), whoIAm, Toast.LENGTH_LONG).show();
        */
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                Core.findServer(getApplicationContext());

                runOnUiThread(new Runnable() {
        */
        /*
        Core.findServer(getApplicationContext());
        Log.set(LEVEL_DEBUG);
        if(Core.whoAmI().equals("Server")) {
            Core.fillDataBase();
        }
        */
    }

}

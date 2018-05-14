package parkettklub.smartcheckroom.core;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class NetworkRunnable implements Runnable {

    private Handler h;

    public NetworkRunnable(Handler h) {
        this.h = h;
    }

    @Override
    public void run() {

        //Core.findServer();

        Message m = Message.obtain(); //get null message
        Bundle b = new Bundle();
        b.putString("udd", "daju");
        m.setData(b);
        //use the handler to send message
        h.sendMessage(m);


    }

}

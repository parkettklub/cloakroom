package parkettklub.smartcheckroom;

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

        SugarDb db = new SugarDb(this);


        String networkString = Core.findServer(getApplicationContext());
        Log.set(LEVEL_DEBUG);
        if(networkString.equals("Server")) {
            Core.fillDataBase();
        }
    }

}

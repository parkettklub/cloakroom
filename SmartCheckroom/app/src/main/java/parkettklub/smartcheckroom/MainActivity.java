package parkettklub.smartcheckroom;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.esotericsoftware.minlog.Log;
import com.orm.SugarDb;

import java.io.IOException;

import parkettklub.smartcheckroom.adapter.FragmentPagerAdapter;
import parkettklub.smartcheckroom.core.Core;
import parkettklub.smartcheckroom.core.driver.networkdriver.NetworkDriver;

import static com.esotericsoftware.minlog.Log.LEVEL_DEBUG;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);


        Log.set(LEVEL_DEBUG);

        if(Core.networkState.equals("Server"))
        {
            Core.showAlertMessage(this, Core.networkState);
        }

        /*
        SugarDb db = new SugarDb(this);


        String networkString = Core.findServer(this.getApplicationContext());
        Toast.makeText(this, networkString, Toast.LENGTH_LONG).show();
        Log.set(LEVEL_DEBUG);
        if(networkString.equals("Server")) {
            Core.fillDataBase();
        }
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_history) {
            Intent i = new Intent();
            //i.setClass(getActivity(), ItemCreateActivity.class);
            i.setClass(this, HistoryActivity.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.action_checkroomState) {
            Intent i = new Intent();
            //i.setClass(getActivity(), ItemCreateActivity.class);
            i.setClass(this, CheckroomStateActivity.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.action_whoAmI) {

            String networkState = Core.whoAmI();

            if(networkState.equals("Server"))
            {
                Core.showAlertMessage(this,"You are a "+ networkState +"! \n\n" +
                        "If you think you should be a Client, then press Find Server button!");
            }
            else
            {
                Core.showAlertMessage(this,"You are a "+ networkState +"!");
            }

            //Toast.makeText(this, networkState, Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings selected", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /*
    private void initNetwork(final Context context)
    {
        Handler handler;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Core.findServer(context);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String whoIAm = Core.whoAmI();

                        Toast.makeText(context, whoIAm, Toast.LENGTH_LONG).show();

                        if (whoIAm.equals("Server")) {
                            Core.showAlertMessage(context,"Please discuss with your colleagues that you are the only server in the network! \n\n" +
                                    "If not then press Find Server button!");
                        }
                    }
                });
            }
        }).start();
    }
    */
    /*
    private void showAlertMessage(final String aMessage) {
        AlertDialog.Builder alertbox =
                new AlertDialog.Builder(this);
        alertbox.setMessage(aMessage);
        alertbox.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0,
                                        int arg1) {
// Ok kiválasztva
                    }
                });
        alertbox.setNegativeButton("Find Server",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0,
                                        int arg1) {

                        Core.closeNetworkConnections();
                        Core.findServer(getApplicationContext());

                        //Toast.makeText(getApplicationContext(), Core.whoAmI(), Toast.LENGTH_LONG).show();
                    }
                });
        alertbox.show();
    }
    */
}

package parkettklub.smartcheckroom;

import android.content.DialogInterface;
import android.content.Intent;
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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        SugarDb db = new SugarDb(this);
        Core.fillDataBase();

        String networkString = Core.findServer(this.getApplicationContext());
        Toast.makeText(this, networkString, Toast.LENGTH_LONG).show();
        Log.set(LEVEL_DEBUG);
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

        if (id == R.id.action_Server) {

            NetworkDriver network = NetworkDriver.getInstance();

            try {
                network.runServer();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Toast.makeText(this, serverString, Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_Client) {

            final NetworkDriver network = NetworkDriver.getInstance();

            try {
                network.runClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAlertMessage(final String aMessage) {
        AlertDialog.Builder alertbox =
                new AlertDialog.Builder(this);
        alertbox.setMessage(aMessage);
        alertbox.setNeutralButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0,
                                        int arg1) {
// Ok kiválasztva
                    }
                });
        alertbox.show();
    }
}

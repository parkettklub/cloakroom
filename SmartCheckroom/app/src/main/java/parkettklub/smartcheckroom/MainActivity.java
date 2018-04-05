package parkettklub.smartcheckroom;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.orm.SugarDb;

import java.util.Date;

import parkettklub.smartcheckroom.adapter.LauncherPagerAdapter;
import parkettklub.smartcheckroom.data.CheckroomItem;
import parkettklub.smartcheckroom.data.ManageDB;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new LauncherPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        SugarDb db = new SugarDb(this);

        for(int i=0; i < 100; i++)
        {
            CheckroomItem item = new CheckroomItem(false, "0000", 0,
                    new Date(System.currentTimeMillis()));
            item.save();
        }
        //db.onCreate(db.getWritableDatabase());
    }
}

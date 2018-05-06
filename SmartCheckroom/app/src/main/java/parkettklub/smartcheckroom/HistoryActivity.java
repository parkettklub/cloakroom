package parkettklub.smartcheckroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import parkettklub.smartcheckroom.adapter.CheckroomTransactionsAdapter;
import parkettklub.smartcheckroom.core.Core;
import parkettklub.smartcheckroom.core.Transaction;
import parkettklub.smartcheckroom.core.driver.dbdriver.CheckroomTransaction;
import parkettklub.smartcheckroom.view.EmptyRecyclerView;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckroomTransactionsAdapter adapter;
    private EmptyRecyclerView emptyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        new Thread(new Runnable() {
            @Override
            public void run() {

                final List<Transaction> transactions =  Core.listAllTransactions();

                //final List<CheckroomTransaction> transactions = CheckroomTransaction.listAll(CheckroomTransaction.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        emptyRecyclerView = findViewById(R.id.checkroomTransactionListERV);
                        emptyRecyclerView.setLayoutManager(new LinearLayoutManager((getApplicationContext())));
                        adapter = new CheckroomTransactionsAdapter(transactions, HistoryActivity.this);
                        emptyRecyclerView.setAdapter(adapter);
                        registerForContextMenu(emptyRecyclerView);

                        View emptyTV = findViewById(R.id.emptyTV);
                        emptyRecyclerView.setEmptyView(emptyTV);
                    }
                });
            }
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_stats) {
            Intent i = new Intent();
            //i.setClass(getActivity(), ItemCreateActivity.class);
            i.setClass(this, StatisticsActivity.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings selected", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

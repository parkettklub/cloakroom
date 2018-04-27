package parkettklub.smartcheckroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import parkettklub.smartcheckroom.adapter.CheckroomTransactionsAdapter;
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
                final List<CheckroomTransaction> transactions = CheckroomTransaction.listAll(CheckroomTransaction.class);
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

}

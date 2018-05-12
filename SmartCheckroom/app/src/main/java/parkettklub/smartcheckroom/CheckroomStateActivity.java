package parkettklub.smartcheckroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import parkettklub.smartcheckroom.adapter.CheckroomItemsAdapter;
import parkettklub.smartcheckroom.adapter.CheckroomTransactionsAdapter;
import parkettklub.smartcheckroom.core.Core;
import parkettklub.smartcheckroom.core.Item;
import parkettklub.smartcheckroom.view.EmptyRecyclerView;

public class CheckroomStateActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckroomItemsAdapter adapter;
    private EmptyRecyclerView emptyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkroom_state);

        new Thread(new Runnable() {
            @Override
            public void run() {

                final List<Item> items =  Core.listAllItems();

                //final List<CheckroomTransaction> transactions = CheckroomTransaction.listAll(CheckroomTransaction.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        emptyRecyclerView = findViewById(R.id.checkroomItemListERV);
                        emptyRecyclerView.setLayoutManager(new LinearLayoutManager((getApplicationContext())));
                        adapter = new CheckroomItemsAdapter(items, CheckroomStateActivity.this);
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

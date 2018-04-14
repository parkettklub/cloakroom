package parkettklub.smartcheckroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import parkettklub.smartcheckroom.data.CheckroomTransaction;

public class HistoryActivity extends AppCompatActivity {

    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        tvStatus = (TextView) findViewById(R.id.tvStatus);

        listAllTransactions();

    }

    private void listAllTransactions() {
        List<CheckroomTransaction> transactions = CheckroomTransaction.listAll(CheckroomTransaction.class);
        tvStatus.setText("");
        for (CheckroomTransaction transaction : transactions) {
            tvStatus.append(transaction.getDueTransactionType()+ ", " +
                            transaction.getDueCheckroomNumber().toString() + ", " +
                            transaction.getDueCoatNumber().toString() + ", " +
                            transaction.getDueDate().toString() + "\n");
        }
    }
}

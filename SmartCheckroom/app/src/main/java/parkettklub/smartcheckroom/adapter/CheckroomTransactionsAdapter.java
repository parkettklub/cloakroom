package parkettklub.smartcheckroom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import parkettklub.smartcheckroom.R;
import parkettklub.smartcheckroom.core.Transaction;
import parkettklub.smartcheckroom.core.driver.dbdriver.CheckroomTransaction;

/**
 * Created by Badbeloved on 2018. 04. 15..
 */

public class CheckroomTransactionsAdapter extends
        RecyclerView.Adapter<CheckroomTransactionsAdapter.TransactionsViewHolder> {

    private List<Transaction> checkroomTransactions;
    private Context context;

    public CheckroomTransactionsAdapter(List<Transaction> checkroomTransactions, Context context) {
        this.checkroomTransactions = checkroomTransactions;
        this.context = context;
    }

    @Override
    public TransactionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_transaction, parent, false);
        return new TransactionsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionsViewHolder holder, int position) {

        final Transaction transaction = checkroomTransactions.get(position);
        if (transaction != null) {
            //holder.tvTransactionId.setText(transaction.getId().toString());
            holder.tvTransactionType.setText(transaction.getTransactionType());
            //holder.tvBarcodeNumber.setText(transaction.getBarcode());
            holder.tvCheckroomNumber.setText(transaction.getCheckroomNum().toString());

            holder.tvCoatNum.setText(transaction.getStuff().toString());
            holder.tvDate.setText(transaction.getTransactionTime().getHours() + ":" +
                    transaction.getTransactionTime().getMinutes() + ":"+
                    transaction.getTransactionTime().getSeconds());
        }
    }

    @Override
    public int getItemCount() {
        return checkroomTransactions.size();
    }

    class TransactionsViewHolder extends RecyclerView.ViewHolder {
        //TextView tvTransactionId;
        TextView tvTransactionType;
        //TextView tvBarcodeNumber;
        TextView tvCheckroomNumber;
        TextView tvCoatNum;
        TextView tvDate;

        public TransactionsViewHolder(View itemView) {
            super(itemView);

            //tvTransactionId = itemView.findViewById(R.id.tvTranscationId);
            tvTransactionType = itemView.findViewById(R.id.tvTranscationType);
            //tvBarcodeNumber = itemView.findViewById(R.id.tvBarcodeNumber);
            tvCheckroomNumber = itemView.findViewById(R.id.tvCheckroomNumber);
            tvCoatNum = itemView.findViewById(R.id.tvCoatNum);
            tvDate = itemView.findViewById(R.id.tvDueDate);
        }
    }
}

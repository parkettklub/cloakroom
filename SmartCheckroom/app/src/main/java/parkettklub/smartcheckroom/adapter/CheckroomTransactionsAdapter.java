package parkettklub.smartcheckroom.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;

import java.util.List;

import parkettklub.smartcheckroom.R;
import parkettklub.smartcheckroom.data.CheckroomTransaction;

/**
 * Created by Badbeloved on 2018. 04. 15..
 */

public class CheckroomTransactionsAdapter extends
        RecyclerView.Adapter<CheckroomTransactionsAdapter.TransactionsViewHolder> {

    private List<CheckroomTransaction> checkroomTransactions;
    private Context context;

    public CheckroomTransactionsAdapter(List<CheckroomTransaction> checkroomTransactions, Context context) {
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

        final CheckroomTransaction transaction = checkroomTransactions.get(position);
        if (transaction != null) {
            holder.tvTransactionId.setText(transaction.getId().toString());
            holder.tvTransactionType.setText(transaction.getDueTransactionType());
            holder.tvBarcodeNumber.setText(transaction.getDueBarcodeNumber());
            holder.tvCheckroomNumber.setText(transaction.getDueCheckroomNumber().toString());

            holder.tvCoatNum.setText("Coats: " + transaction.getDueCoatNumber().toString());
            holder.tvDate.setText(transaction.getDueDate().toString());
        }
    }

    @Override
    public int getItemCount() {
        return checkroomTransactions.size();
    }

    class TransactionsViewHolder extends RecyclerView.ViewHolder {
        TextView tvTransactionId;
        TextView tvTransactionType;
        TextView tvBarcodeNumber;
        TextView tvCheckroomNumber;
        TextView tvCoatNum;
        TextView tvDate;

        public TransactionsViewHolder(View itemView) {
            super(itemView);

            tvTransactionId = itemView.findViewById(R.id.tvTranscationId);
            tvTransactionType = itemView.findViewById(R.id.tvTranscationType);
            tvBarcodeNumber = itemView.findViewById(R.id.tvBarcodeNumber);
            tvCheckroomNumber = itemView.findViewById(R.id.tvCheckroomNumber);
            tvCoatNum = itemView.findViewById(R.id.tvCoatNum);
            tvDate = itemView.findViewById(R.id.tvDueDate);
        }
    }
}

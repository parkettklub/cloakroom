package parkettklub.smartcheckroom.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import parkettklub.smartcheckroom.R;
import parkettklub.smartcheckroom.core.Item;

public class CheckroomItemsAdapter extends
        RecyclerView.Adapter<CheckroomItemsAdapter.ItemsViewHolder> {

    private List<Item> checkroomItems;
    private Context context;

    public CheckroomItemsAdapter(List<Item> checkroomItems, Context context) {
        this.checkroomItems = checkroomItems;
        this.context = context;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_item, parent, false);
        return new ItemsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CheckroomItemsAdapter.ItemsViewHolder holder, int position) {

        final Item item = checkroomItems.get(position);
        if (item != null) {
            holder.tvCheckroomNum.setText(item.getCheckroomNum().toString());
            if(item.getBarcode().equals(""))
            {
                holder.tvCheckroomNum.setBackgroundColor(Color.WHITE);
            }
            else
            {
                holder.tvCheckroomNum.setBackgroundColor(Color.RED);
            }
        }
    }

    @Override
    public int getItemCount() {
        return checkroomItems.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {
        TextView tvCheckroomNum;
        //TextView tvTransactionType;
        //TextView tvBarcodeNumber;


        public ItemsViewHolder(View itemView) {
            super(itemView);

            tvCheckroomNum = itemView.findViewById(R.id.tvCheckroomNum);
        }
    }
}

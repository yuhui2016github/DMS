package com.example.yuhui.dms.dmscatalogue.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.yuhui.dms.R;

import java.util.List;

/**
 * Created by yuhui on 2016-8-22.
 */
public class PurchaseOrderListAdapter extends RecyclerView.Adapter<PurchaseOrderListAdapter.PurchaseOrderViewHolder> {
    private Context context;
    private List dataLsit;

    public PurchaseOrderListAdapter(Context context, List dataLsit) {
        this.context = context;
        this.dataLsit = dataLsit;
    }

    @Override
    public PurchaseOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.purchase_order_list_item, parent, false);
        return new PurchaseOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PurchaseOrderViewHolder holder, int position) {
        holder.productDetailLayout.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return dataLsit.size();
    }

    class PurchaseOrderViewHolder extends RecyclerView.ViewHolder {
        LinearLayout productDetailLayout;

        public PurchaseOrderViewHolder(View itemView) {
            super(itemView);
            productDetailLayout = (LinearLayout) itemView.findViewById(R.id.shopping_car_child_item);
        }
    }
}

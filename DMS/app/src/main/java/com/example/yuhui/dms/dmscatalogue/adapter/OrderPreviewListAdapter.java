package com.example.yuhui.dms.dmscatalogue.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.view.TextTagView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuhui on 2016-8-26.
 */
public class OrderPreviewListAdapter extends RecyclerView.Adapter<OrderPreviewListAdapter.OrderPreviewHolder> {
    private Context context;
    private List dataList;

    public OrderPreviewListAdapter(Context context, List dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public OrderPreviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_preview_list_item, parent, false);
        OrderPreviewHolder orderPreviewHolder = new OrderPreviewHolder(view);
        return orderPreviewHolder;
    }

    @Override
    public void onBindViewHolder(OrderPreviewHolder holder, int position) {
        List distributionDataList = new ArrayList<String>();
        distributionDataList.add("添加商品");
        distributionDataList.add("自提");
        holder.distributionType.setLayoutManager(new GridLayoutManager(context, 2));
        holder.distributionType.setData(distributionDataList);
        List productImageList = new ArrayList<Integer>(Arrays.asList(R.drawable.pullloading1,
                R.drawable.pullloading2, R.drawable.pullloading3, R.drawable.pullloading4,
                R.drawable.pullloading1, R.drawable.pullloading2, R.drawable.pullloading3));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.productsPreviewView.setLayoutManager(linearLayoutManager);
        ProductImagePreviewAdapter productImagePreviewAdapter
                = new ProductImagePreviewAdapter(context, productImageList);
        holder.productsPreviewView.setAdapter(productImagePreviewAdapter);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class OrderPreviewHolder extends RecyclerView.ViewHolder {
        TextTagView distributionType;
        RecyclerView productsPreviewView;

        public OrderPreviewHolder(View itemView) {
            super(itemView);
            distributionType = (TextTagView) itemView.findViewById(R.id.distribution_type);
            productsPreviewView = (RecyclerView) itemView.findViewById(R.id.products_preview);
        }
    }
}

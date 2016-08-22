package com.example.yuhui.dms.dmscatalogue.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.adapter.PurchaseOrderListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuhui on 2016-8-22.
 */
public class PurchaseOrderListFragment extends Fragment {
    private List<String> dataList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.purchase_order_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView orderList = (RecyclerView) view.findViewById(R.id.purchase_order_list);
        dataList = new ArrayList(Arrays.asList("1", "2", "3", "4"));
        PurchaseOrderListAdapter purchaseOrderListAdapter
                = new PurchaseOrderListAdapter(getContext(), dataList);
        orderList.setLayoutManager(new LinearLayoutManager(getContext()));
        orderList.setAdapter(purchaseOrderListAdapter);
    }
}

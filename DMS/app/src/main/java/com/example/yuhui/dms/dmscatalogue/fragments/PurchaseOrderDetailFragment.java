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
import com.example.yuhui.dms.dmscatalogue.bean.ProductBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuhui on 2016-8-23.
 */
public class PurchaseOrderDetailFragment extends Fragment {
    private List<String> dataList;
    private Object data;

    public void setData(Object data) {
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.purchase_order_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView orderList = (RecyclerView) view.findViewById(R.id.purchase_order_list);
        dataList = new ArrayList(Arrays.asList("1", "2", "3", "4"));

        List testList = new ArrayList();
        for (int i = 0; i < 4; i++) {
            ProductBean productBean = new ProductBean(i + "", "第 " + i + " 产品 ");
            productBean.setUnitPrice(60);
            productBean.setGiftDetail("康师傅葡萄味饮料250ml*24 2箱");
            productBean.setAmount(5);
            productBean.setIsValid(true);
            productBean.setImageUri("http://pic25.nipic.com/20121201/11501528_124108168130_2.jpg");
            testList.add(productBean);
        }

        PurchaseOrderListAdapter purchaseOrderDetailAdapter
                = new PurchaseOrderListAdapter(getContext(), dataList);
        purchaseOrderDetailAdapter.setShowDetail(true);
        purchaseOrderDetailAdapter.setProductDetailList(testList);
        orderList.setLayoutManager(new LinearLayoutManager(getContext()));
        orderList.setAdapter(purchaseOrderDetailAdapter);
    }
}

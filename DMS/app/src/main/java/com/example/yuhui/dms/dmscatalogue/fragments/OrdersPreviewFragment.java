package com.example.yuhui.dms.dmscatalogue.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.adapter.ProductImagePreviewAdapter;
import com.example.yuhui.dms.dmscatalogue.view.TextTagView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuhui on 2016-8-19.
 */
public class OrdersPreviewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.orders_preview_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextTagView distributionType = (TextTagView) view.findViewById(R.id.distribution_type);
        List distributionDataList = new ArrayList<String>();
        distributionDataList.add("添加商品");
        distributionDataList.add("自提");
        distributionType.setLayoutManager(new GridLayoutManager(getContext(), 2));
        distributionType.setData(distributionDataList);


        List productImageList = new ArrayList<Integer>(Arrays.asList(R.drawable.pullloading1,
                R.drawable.pullloading2, R.drawable.pullloading3, R.drawable.pullloading4,
                R.drawable.pullloading1, R.drawable.pullloading2, R.drawable.pullloading3));
        RecyclerView productsPreviewView = (RecyclerView) view.findViewById(R.id.products_preview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        productsPreviewView.setLayoutManager(linearLayoutManager);
        ProductImagePreviewAdapter productImagePreviewAdapter
                = new ProductImagePreviewAdapter(getContext(), productImageList);
        productsPreviewView.setAdapter(productImagePreviewAdapter);

    }
}

package com.example.yuhui.dms.dmscatalogue.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.view.TextTagView;

import java.util.ArrayList;
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
        TextTagView textTagView = (TextTagView) view.findViewById(R.id.distribution_type);
        List dataList = new ArrayList<String>();
        dataList.add("添加商品");
        dataList.add("自提");
        textTagView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        textTagView.setData(dataList);
    }
}

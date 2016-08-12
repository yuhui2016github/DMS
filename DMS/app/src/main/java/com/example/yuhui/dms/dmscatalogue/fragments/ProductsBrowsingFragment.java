package com.example.yuhui.dms.dmscatalogue.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.adapter.ProductsBrowsingAdapter;
import com.example.yuhui.dms.dmscatalogue.view.ProductsBrowsingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuhui on 2016-8-10.
 */
public class ProductsBrowsingFragment extends Fragment {
    private ProductsBrowsingView productsBrowsingView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.products_browsing_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            dataList.add(i + "");
        }
        ProductsBrowsingAdapter adapter = new ProductsBrowsingAdapter(getActivity(), dataList);
        productsBrowsingView = (ProductsBrowsingView) view.findViewById(R.id.products_browsing_view);
        productsBrowsingView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter.setImageOnclickListener(new ProductsBrowsingAdapter.ProductImageOnclickListener() {
            @Override
            public void onClick(int position, Object data) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_view, new ProductDetailFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        productsBrowsingView.setAdapter(adapter);
    }
}

package com.example.yuhui.dms.dmscatalogue.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.adapter.OrderPreviewListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuhui on 2016-8-19.
 */
public class OrdersPreviewFragment extends Fragment {

    private List<String> dataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.orders_preview_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getString(R.string.oreder_preview));
        initViews(view);
    }

    private void initViews(View view) {
        RecyclerView rvOrderPreview = (RecyclerView) view.findViewById(R.id.rv_order_preview);
        rvOrderPreview.setLayoutManager(new LinearLayoutManager(getContext()));

        dataList = new ArrayList(Arrays.asList("1", "2", "3", "4"));
        OrderPreviewListAdapter orderPreviewListAdapter
                = new OrderPreviewListAdapter(getContext(), dataList);
        rvOrderPreview.setAdapter(orderPreviewListAdapter);
        Button leftButton = (Button) view.findViewById(R.id.btn_left);
        leftButton.setVisibility(View.GONE);
        Button rightButton = (Button) view.findViewById(R.id.btn_right);
        rightButton.setText(R.string.submit_order


        );
//        rightButton.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

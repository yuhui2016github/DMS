package com.example.yuhui.dms.dmscatalogue.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.adapter.ShoppingCarListAdapter;
import com.example.yuhui.dms.dmscatalogue.bean.ProductBean;
import com.example.yuhui.dms.dmscatalogue.bean.StoreBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuhui on 2016-8-15.
 */
public class ShoppingCarFragment extends Fragment {
    private static final int MENU_ITEM_EDIT = 0;
    private static final int MENU_ITEM_DONE = 1;
    private Menu menu;
    private ExpandableListView shoppingListView;
    private ShoppingCarListAdapter shoppingCarListAdapter;
    private List<Object> groupMapList;
    private List<List<Object>> childMapList;
    private Button rightButton;
    private Button leftButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shopping_car_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shoppingListView = (ExpandableListView) view.findViewById(R.id.shopping_list);
        initData();
        shoppingCarListAdapter = new ShoppingCarListAdapter(getContext(), groupMapList, childMapList);
        shoppingListView.setAdapter(shoppingCarListAdapter);
        //expand all groups
        for (int index = 0; index < groupMapList.size(); index++) {
            shoppingListView.expandGroup(index);
        }
        shoppingListView.setVerticalScrollBarEnabled(false);
//        shoppingListView.smoothScrollToPosition(0);
        Button rightButton = (Button) view.findViewById(R.id.button_right);
        rightButton.setText(R.string.to_settle_acounts);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_view, new OrdersPreviewFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void initData() {
        groupMapList = new ArrayList<>();
        childMapList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            groupMapList.add(new StoreBean(i + "", true, "店铺 " + (i + 1)));
            List innerList = new ArrayList();
            for (int j = 0; j < i + 1 && j < 4; j++) {
                ProductBean productBean = new ProductBean(j + "", "第 " + i + " 店铺的商品 " + j);
                productBean.setUnitPrice(60 + "");
                productBean.setGiftDetail("康师傅葡萄味饮料250ml*24 2箱");
                productBean.setAmount(5);
                productBean.setIsValid(true);
                productBean.setImageUri("http://pic25.nipic.com/20121201/11501528_124108168130_2.jpg");
                innerList.add(productBean);
            }
            childMapList.add(innerList);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        inflater.inflate(R.menu.menu_shopping_car, menu);
        menu.getItem(MENU_ITEM_DONE).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(true);
        }
        switch (item.getItemId()) {
            case R.id.edit:
                item.setVisible(false);
                break;
            case R.id.done:
                item.setVisible(false);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

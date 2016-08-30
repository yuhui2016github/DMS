package com.example.yuhui.dms.dmscatalogue.fragments;

import android.os.Bundle;
import android.support.annotation.IntDef;
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
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yuhui.dms.R;
import com.example.yuhui.dms.Utils;
import com.example.yuhui.dms.dmscatalogue.adapter.ShoppingCarListAdapter;
import com.example.yuhui.dms.dmscatalogue.bean.ProductBean;
import com.example.yuhui.dms.dmscatalogue.bean.StoreBean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuhui on 2016-8-15.
 */
public class ShoppingCarFragment extends Fragment implements View.OnClickListener, ShoppingCarListAdapter.OnProductCheckedChangeListener {
    private static final int MENU_ITEM_EDIT = 0;
    private static final int MENU_ITEM_DONE = 1;
    private static final int STATUS_VIEW = 1000;
    private static final int STATUS_EDIT = 1001;
    private static final String TAG = "ShoppingCarFragment";
    private int status = STATUS_VIEW;
    private Menu menu;
    private ExpandableListView shoppingListView;
    private ShoppingCarListAdapter shoppingCarListAdapter;
    private List<Object> groupList;
    private List<List<Object>> childList;
    private Button rightButton;
    private Button leftButton;
    private CheckBox footCheckBox;
    private LinearLayout totalPriceLayout;
    private TextView totalPriceTv;
    private int mMenuOpenedHeight;//编辑菜单打开时的高度
    private boolean mIsKeyboardOpened;// 软键盘是否显示

    @IntDef({STATUS_VIEW, STATUS_EDIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StatusType {

    }

    public void setStatus(@StatusType int status) {
        this.status = status;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shopping_car_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getString(R.string.shopping_cart));
        initFootView(view);
        shoppingListView = (ExpandableListView) view.findViewById(R.id.shopping_list);
        shoppingListView.setVerticalScrollBarEnabled(false);
        initData();
        shoppingCarListAdapter = new ShoppingCarListAdapter(getContext(), groupList, childList);
        shoppingCarListAdapter.setGlobalCheckedChangeListener(
                new ShoppingCarListAdapter.GlobalCheckedChangeListener() {
                    @Override
                    public void onGlobalCheckedChanged(boolean isChecked) {
                        footCheckBox.setChecked(isChecked);
                    }
                });
        shoppingCarListAdapter.setOnProductCheckedChangeListener(this);
        shoppingCarListAdapter.dealPrice();
        shoppingListView.setAdapter(shoppingCarListAdapter);
        //expand all groups
        for (int index = 0; index < groupList.size(); index++) {
            shoppingListView.expandGroup(index);
        }
        shoppingListView.smoothScrollToPosition(0);
    }

    private void initFootView(View view) {
        footCheckBox = (CheckBox) view.findViewById(R.id.select_all);
        footCheckBox.setOnClickListener(this);
        totalPriceLayout = (LinearLayout) view.findViewById(R.id.total_price_layout);
        totalPriceTv = (TextView) view.findViewById(R.id.tv_total_price);
        leftButton = (Button) view.findViewById(R.id.btn_left);
        leftButton.setText(R.string.continue_order_goods);
        leftButton.setOnClickListener(this);
        rightButton = (Button) view.findViewById(R.id.btn_right);
        rightButton.setText(R.string.settle_acounts);
        rightButton.setOnClickListener(this);
    }

    private void initData() {
        groupList = new ArrayList<>();
        childList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            groupList.add(new StoreBean(i + "", true, "店铺 " + (i + 1)));
            List innerList;
            innerList = new ArrayList();
            for (int j = 0; j < i + 1 && j < 4; j++) {
                ProductBean productBean = new ProductBean(j + "", "第 " + i + " 店铺的商品 " + j);
                productBean.setUnitPrice(60);
                productBean.setGiftDetail("康师傅葡萄味饮料250ml*24 2箱");
                productBean.setAmount(5);
                productBean.setIsValid(true);
                productBean.setImageUri("http://pic25.nipic.com/20121201/11501528_124108168130_2.jpg");
                innerList.add(productBean);
            }
            childList.add(innerList);
        }
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
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.edit:
                setStatus(STATUS_EDIT);
                menu.getItem(MENU_ITEM_EDIT).setVisible(false);
                shoppingCarListAdapter.notifyDataSetChanged();
                footCheckBox.setVisibility(View.VISIBLE);
                totalPriceLayout.setVisibility(View.GONE);
                leftButton.setText(getString(R.string.remove_invalid_products));
                rightButton.setText(getString(R.string.delete));
                break;
            case R.id.done:
                setStatus(STATUS_VIEW);
                menu.getItem(MENU_ITEM_DONE).setVisible(false);
                shoppingCarListAdapter.notifyDataSetChanged();
                footCheckBox.setVisibility(View.GONE);
                totalPriceLayout.setVisibility(View.VISIBLE);
                leftButton.setText(getString(R.string.continue_order_goods));
                rightButton.setText(getString(R.string.settle_acounts));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (v.getId()) {
            case R.id.btn_left:
                if (status == STATUS_VIEW) {
                    fragmentTransaction.replace(R.id.fragment_view, new ProductsBrowsingFragment());
                    fragmentTransaction.addToBackStack(ShoppingCarFragment.class.getName());
                    fragmentTransaction.commit();
                } else if (status == STATUS_EDIT) {
                    shoppingCarListAdapter.removeInvalidProducts();
                }
                break;

            case R.id.btn_right:
                if (status == STATUS_VIEW) {
                    fragmentTransaction.replace(R.id.fragment_view, new OrdersPreviewFragment());
                    fragmentTransaction.addToBackStack(ShoppingCarFragment.class.getName());
                    fragmentTransaction.commit();
                } else if (status == STATUS_EDIT) {
                    shoppingCarListAdapter.removeProducts();
                }
                break;

            case R.id.select_all:
                boolean isChecked = footCheckBox.isChecked();
                int groupPosition = 0;
                for (Object storeBean : groupList) {
                    ((StoreBean) storeBean).setIsChecked(isChecked);
                    for (Object productBean : childList.get(groupPosition)) {
                        ((ProductBean) productBean).setIsChecked(isChecked);
                    }
                    groupPosition++;
                }
                shoppingCarListAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void onProductCheckedChange(int totalCount, float totalPrice) {
        totalPriceTv.setText(Utils.formatNumber(totalPrice));
        if (status == STATUS_VIEW) {
            rightButton.setText(String.format(getString(R.string.settle_acounts),totalCount));
        }
    }
}

package com.example.yuhui.dms.dmscatalogue.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.adapter.ProductsBrowsingAdapter;
import com.example.yuhui.dms.dmscatalogue.view.ProductsBrowsingView;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品目录浏览Fragment
 * Created by yuhui on 2016-8-10.
 */
public class ProductsBrowsingFragment extends Fragment {

    private static final String TAG = "ProductsBrowsingFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.products_browsing_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getString(R.string.products_browsing));
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            dataList.add("第 " + i
                    + " 项商品： 国行Apple/苹果iwatch智能手表/国行Apple/苹果iwatch智能手表/");
        }
        ProductsBrowsingAdapter adapter = new ProductsBrowsingAdapter(getActivity(), dataList);
        ProductsBrowsingView productsBrowsingView = (ProductsBrowsingView) view.findViewById(R.id.products_browsing_view);
        productsBrowsingView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter.setImageOnclickListener(new ProductsBrowsingAdapter.ProductImageOnclickListener() {
            @Override
            public void onClick(int position, Object data) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_view, new ProductDetailFragment());
                fragmentTransaction.addToBackStack(ProductsBrowsingFragment.class.getName());
                fragmentTransaction.commit();
            }
        });
        productsBrowsingView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_product_browser_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                int num = getActivity().getSupportFragmentManager().getBackStackEntryCount();
                if (num > 0) {
                    getActivity().onBackPressed();
                } else {
                    getActivity().finish();
                }
                return true;
            case R.id.shopping_car:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if (!fragmentManager.popBackStackImmediate(ShoppingCarFragment.class.getName(),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE)) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_view, new ShoppingCarFragment());
                    fragmentTransaction.addToBackStack(ProductsBrowsingFragment.class.getName());
                    fragmentTransaction.commit();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

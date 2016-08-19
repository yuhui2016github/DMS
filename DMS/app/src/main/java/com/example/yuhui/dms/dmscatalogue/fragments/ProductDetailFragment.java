package com.example.yuhui.dms.dmscatalogue.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ScrollView;

import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.adapter.ImagePagerAdapter;
import com.example.yuhui.dms.dmscatalogue.dialog.ShoppingFormInputDialog;

/**
 * Created by yuhui on 2016-8-12.
 */
public class ProductDetailFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private ViewGroup group;
    private int[] imgIdArray;
    ImagePagerAdapter imagePagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        group = (ViewGroup) view.findViewById(R.id.view_group);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        //载入图片资源ID
        imgIdArray = new int[]{R.drawable.pullloading1, R.drawable.pullloading2, R.drawable.pullloading3,
                R.drawable.pullloading4};

        imagePagerAdapter = new ImagePagerAdapter.Builder(getContext())
                .setImgIdArray(imgIdArray)
                .setGroup(group)
                .setLoop(false)
                .create();
        //设置Adapter
        viewPager.setAdapter(imagePagerAdapter);
        //设置监听，主要是设置点点的背景
        viewPager.addOnPageChangeListener(this);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左(循环)滑动
        viewPager.setCurrentItem(imagePagerAdapter.getLoop() ? ((imgIdArray.length) * 100) : 0);
        ScrollView detailScroll = (ScrollView) view.findViewById(R.id.product_detail_scroll);
        detailScroll.setVerticalScrollBarEnabled(false);

        Drawable drawable = getResources().getDrawable(R.drawable.product_detail_collection_selection);
        CheckBox collect = (CheckBox) view.findViewById(R.id.checkbox_collection);
        drawable.setBounds(0, 0, 60, 60);
        collect.setCompoundDrawables(null, drawable, null, null);

        drawable = getResources().getDrawable(R.drawable.product_detail_shopping_selection);
        CheckBox addToShoppingCar = (CheckBox) view.findViewById(R.id.checkbox_add_shoppingcar);
        drawable.setBounds(0, 0, 60, 60);
        addToShoppingCar.setCompoundDrawables(null, drawable, null, null);
        addToShoppingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShoppingFormInputDialog.Builder(getContext()).setData(80).show();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        imagePagerAdapter.setImageBackground(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_product_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shopping_car:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_view, new ShoppingCarFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}





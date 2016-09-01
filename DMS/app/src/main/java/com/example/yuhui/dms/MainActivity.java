package com.example.yuhui.dms;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.yuhui.dms.dmscatalogue.DisplayMode;
import com.example.yuhui.dms.dmscatalogue.fragments.ShoppingCarFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMode.setIsDisplayImage(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.fragment_view, new ProductsBrowsingFragment());
        fragmentTransaction.add(R.id.fragment_view, new ShoppingCarFragment());
//        fragmentTransaction.add(R.id.fragment_view, new PurchaseOrderListFragment());
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        initActionBar();

    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void supportInvalidateOptionsMenu() {
        super.supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}

package com.example.yuhui.dms.dmscatalogue.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.yuhui.dms.R;

/**
 * Created by yuhui on 2016-8-15.
 */
public class ShoppingCarFragment extends Fragment {
    private static final int MENU_ITEM_EDIT = 0;
    private static final int MENU_ITEM_DONE = 1;
    Menu menu;

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

package com.example.yuhui.dms.dmscatalogue.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.yuhui.dms.R;

/**
 * Created by yuhui on 2016-8-22.
 */
public class PopupTagView extends LinearLayout {
    private static final String[] data = {"A型", "B型", "O型", "AB型", "其他"};
    private View rootView;

    public PopupTagView(Context context) {
        super(context);
        initViews(context);
    }

    public PopupTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    private void initViews(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.popup_tag_view_layout, this);
        Spinner spinnerView = (Spinner) rootView.findViewById(R.id.spinner_view);
        MySpinnerAdapter adapter = new MySpinnerAdapter(context, R.layout.popup_tag_view_spinner_item, R.id.text_item, data);
        //set your custom adapter
        spinnerView.setAdapter(adapter);
    }

    private class MySpinnerAdapter extends ArrayAdapter {
        public MySpinnerAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
            super(context, resource, textViewResourceId, objects);
        }
    }
}

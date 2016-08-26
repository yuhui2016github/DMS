package com.example.yuhui.dms.dmscatalogue.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.PopupWindow;

import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.adapter.PopTagListAdapter;
import com.example.yuhui.dms.dmscatalogue.interfaces.TagPopupSelectListener;
import com.example.yuhui.dms.dmscatalogue.view.PopupTagView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by yuhui on 2016-8-26.
 */
public class TagPopupWindow extends PopupWindow implements View.OnClickListener {
    private RecyclerView popupListView;
    private DatePicker datePicker;
    private int popupStyle;
    private TagPopupSelectListener tagPopupSelectListener;
    private int year;
    private int month;
    private int day;

    public void setPopupStyle(int popupStyle) {
        this.popupStyle = popupStyle;
        if (popupStyle == PopupTagView.STYLE_LIST) {
            datePicker.setVisibility(View.GONE);
            popupListView.setVisibility(View.VISIBLE);
        } else if (popupStyle == PopupTagView.STYLE_DATEPICKER) {
            datePicker.setVisibility(View.VISIBLE);
            popupListView.setVisibility(View.GONE);
        }
    }

    public TagPopupWindow(Context context, TagPopupSelectListener tagPopupSelectListener) {
        super(context);
        this.tagPopupSelectListener = tagPopupSelectListener;
        initView(context);
        setupWindowParams();
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.tag_popup_window_layout, null);
        Button btnConfirm = (Button) view.findViewById(R.id.btn_confrim);
        btnConfirm.setOnClickListener(this);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        popupListView = (RecyclerView) view.findViewById(R.id.rv_popupList);
        initPopupList(context);
        datePicker = (DatePicker) view.findViewById(R.id.dp_popup);
        initDate();
        this.setContentView(view);
    }

    private void initPopupList(Context context) {
        popupListView.setLayoutManager(new LinearLayoutManager(context));
        PopTagListAdapter popTagListAdapter = new PopTagListAdapter(context);
        List<String> testDataList = new ArrayList(Arrays.asList("预付款", "到货付款", "微信支付", "支付宝"));
        popTagListAdapter.setDataList(testDataList);
        popTagListAdapter.setTagPopupSelectListener(tagPopupSelectListener);
        popupListView.setAdapter(popTagListAdapter);
    }

    private void initDate() {
        TimeZone t = TimeZone.getTimeZone("GMT+08:00");// 获取东8区TimeZone
        Calendar calendar = Calendar.getInstance(t);
        calendar.setTimeInMillis(System.currentTimeMillis());
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, 0, 0, 0);
        datePicker.setMinDate(calendar.getTimeInMillis());
        datePicker.init(year, month, day, new OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int yearOfDatePicker, int monthOfYear, int dayOfMonth) {
                year = yearOfDatePicker;
                month = monthOfYear + 1;
                day = dayOfMonth;
                tagPopupSelectListener.onDateChanged(year, month, day);
            }
        });
    }

    private void setupWindowParams() {
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
        this.setBackgroundDrawable(dw);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confrim:
                if (popupStyle == PopupTagView.STYLE_DATEPICKER) {
                    tagPopupSelectListener.onConfirm(year, month, day);
                }
                dismiss();
                break;
            case R.id.btn_cancel:
                tagPopupSelectListener.onCancel();
                dismiss();
                break;
            default:
                break;
        }
    }

}

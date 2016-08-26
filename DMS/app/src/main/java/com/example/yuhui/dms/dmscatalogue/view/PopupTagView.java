package com.example.yuhui.dms.dmscatalogue.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.dialog.TagPopupWindow;
import com.example.yuhui.dms.dmscatalogue.interfaces.TagPopupSelectListener;

/**
 * Created by yuhui on 2016-8-22.
 */
public class PopupTagView extends LinearLayout implements View.OnClickListener, TagPopupSelectListener {
    public static final int STYLE_LIST = 0;
    public static final int STYLE_DATEPICKER = 1;

    private Context context;
    private int popupStyle;
    private TextView tvContent;
    private TagPopupWindow tagPopupWindow;
    private String oldTextString;

    public PopupTagView(Context context) {
        super(context);
        this.context = context;
        initViews(context);
    }

    public PopupTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(context, attrs);
        initViews(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PopupTagView);
        popupStyle = typedArray.getInt(R.styleable.PopupTagView_popupStyle, 0);
        typedArray.recycle();
    }

    private void initViews(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.popup_tag_view_layout, this);
        tvContent = (TextView) rootView.findViewById(R.id.tv_content);
        tvContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_content:
                oldTextString = tvContent.getText().toString();
                if (tagPopupWindow == null) {
                    tagPopupWindow = new TagPopupWindow(context, this);
                }
                tagPopupWindow.setPopupStyle(popupStyle);
                tagPopupWindow.showAtLocation(this, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDateChanged(int year, int monthOfYear, int dayOfMonth) {
        tvContent.setText(year + " 年 " + monthOfYear + " 月 " + dayOfMonth + " 日 ");
    }

    @Override
    public void onCancel() {
        tvContent.setText(oldTextString);
    }

    @Override
    public void onConfirm(int year, int monthOfYear, int dayOfMonth) {
        tvContent.setText(year + " 年 " + monthOfYear + " 月 " + dayOfMonth + " 日 ");
    }

    @Override
    public void onSelectItem(String item) {
        tvContent.setText(item);
    }
}

package com.example.yuhui.dms.dmscatalogue.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.yuhui.dms.ImageUtils;
import com.example.yuhui.dms.R;

/**
 * Created by yuhui on 2016-8-16.
 */
public class AmountEditView extends LinearLayout implements View.OnClickListener {
    private int amount;
    private ImageView reduceView;
    private ImageView addView;
    private EditText amountEditor;

    public AmountEditView(Context context) {
        super(context);
        initView(context);
    }

    public AmountEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AmountEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(HORIZONTAL);
        reduceView = new ImageView(context);
        reduceView.setImageResource(R.drawable.ic_reduce);
        reduceView.setLayoutParams(new ViewGroup.LayoutParams(ImageUtils.dip2px(context, 30), ImageUtils.dip2px(context, 30)));
        reduceView.setOnClickListener(this);

        addView = new ImageView(context);
        addView.setImageResource(R.drawable.ic_add);
        addView.setLayoutParams(new ViewGroup.LayoutParams(ImageUtils.dip2px(context, 30), ImageUtils.dip2px(context, 30)));
        addView.setOnClickListener(this);

        amountEditor = new EditText(context);
        amountEditor.setLayoutParams(new ViewGroup.LayoutParams(ImageUtils.dip2px(context, 55), ImageUtils.dip2px(context, 35)));

        addView(reduceView);
        addView(amountEditor);
        addView(addView);
        this.setScaleX(0.8f);
        this.setScaleY(0.8f);
    }

    public void setAmount(int amount) {
        this.amount = amount;
        amountEditor.setText(amount + "");
    }

    public int getAmount() {
        amount = Integer.parseInt(amountEditor.getText().toString());
        return amount;
    }

    @Override
    public void onClick(View v) {
        if (v == reduceView) {
            amount--;
        } else if (v == addView) {
            amount++;
        }
        amountEditor.setText(amount + "");
    }
}

package com.example.yuhui.dms.dmscatalogue.view;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.yuhui.dms.R;

/**
 * Created by yuhui on 2016-8-16.
 */
public class AmountEditView extends LinearLayout implements View.OnClickListener {
    private int amount;
    private ImageButton reduceButton;
    private ImageButton addButton;
    private EditText amountEditor;
    private OnAmountChangeListener onAmountChangeListener;

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
        LayoutInflater.from(context).inflate(R.layout.amount_view_layout, this);
        amountEditor = (EditText) findViewById(R.id.etAmount);
        amountEditor.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        amountEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.equals("")) {
                    if (s.length() > 0) {
                        amount = Integer.parseInt(s.toString());
                        if (amount == 0) {
                            amount = 1;
                            callBackAmountChanged();
                            amountEditor.setText(amount + "");
                            return;
                        } else {
                            callBackAmountChanged();
                        }
                    }
                }
            }
        });
        addButton = (ImageButton) findViewById(R.id.btnAdd);
        reduceButton = (ImageButton) findViewById(R.id.btnReduce);
        addButton.setOnClickListener(this);
        reduceButton.setOnClickListener(this);
    }

    private void callBackAmountChanged() {
        if (onAmountChangeListener != null) {
            onAmountChangeListener.onAmountChanged(amount);
        }
    }

    public void setAmount(int amount) {
        this.amount = amount;
        amountEditor.setText(amount + "");
        if (amount == 1) {
            reduceButton.setImageResource(R.drawable.ic_reduce_disable);
        } else {
            reduceButton.setImageResource(R.drawable.ic_reduce);
        }
    }

    public int getAmount() {
        amount = Integer.parseInt(amountEditor.getText().toString());
        return amount;
    }

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.onAmountChangeListener = onAmountChangeListener;
    }

    @Override
    public void onClick(View v) {
        if (v == reduceButton) {
            if (amount <= 1) {
                return;
            }
            if (amount == 2) {
                reduceButton.setImageResource(R.drawable.ic_reduce_disable);
            } else {
                reduceButton.setImageResource(R.drawable.ic_reduce);
            }
            amount--;
        } else if (v == addButton) {
            if (amount >= 99999) {
                return;
            }
            if (amount == 1) {
                reduceButton.setImageResource(R.drawable.ic_reduce);
            }
            amount++;
        }

        amountEditor.setText(amount + "");
        if (onAmountChangeListener != null)

        {
            onAmountChangeListener.onAmountChanged(amount);
        }
    }

    public interface OnAmountChangeListener {
        void onAmountChanged(int amount);
    }
}

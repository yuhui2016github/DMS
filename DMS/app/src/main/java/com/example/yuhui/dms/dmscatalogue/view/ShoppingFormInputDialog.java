package com.example.yuhui.dms.dmscatalogue.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.yuhui.dms.R;

/**
 * Created by yuhui on 2016-8-12.
 */
public class ShoppingFormInputDialog extends Dialog {
    private View rootView;

    public static class Builder {
        public final Context context;
        public int data;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setData(int data) {
            this.data = data;
            return this;
        }

        public ShoppingFormInputDialog create() {
            final ShoppingFormInputDialog dialog = new ShoppingFormInputDialog(this);
            return dialog;
        }

        public ShoppingFormInputDialog show() {
            ShoppingFormInputDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

    protected ShoppingFormInputDialog(Builder builder) {
        super(builder.context, R.style.Translucent_NoTitle);
        initView(builder.context);
        initData(builder.data);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        rootView = inflater.inflate(R.layout.shopping_form_input_dialog, null);
        setWindowProperty();
    }

    // TODO: 2016-8-12
    private void initData(int data) {
        EditText editText = (EditText) rootView.findViewById(R.id.count_input);
        editText.setText(data + "  测试 ");
    }

    private void setWindowProperty() {
        Window win = getWindow();
        win.addContentView(rootView,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        win.setLayout(WindowManager.LayoutParams.MATCH_PARENT
                , WindowManager.LayoutParams.WRAP_CONTENT);
        win.setWindowAnimations(R.style.AnimBottom);
        win.setGravity(Gravity.BOTTOM);
    }
}

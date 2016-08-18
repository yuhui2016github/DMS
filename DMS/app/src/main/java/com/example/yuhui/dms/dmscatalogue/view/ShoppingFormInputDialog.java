package com.example.yuhui.dms.dmscatalogue.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuhui.dms.ImageUtils;
import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.adapter.TagViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuhui on 2016-8-12.
 */
public class ShoppingFormInputDialog extends Dialog {
    private View rootView;
    TextView totalPrice;
    List dataList;

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
            return new ShoppingFormInputDialog(this);
        }

        public ShoppingFormInputDialog show() {
            ShoppingFormInputDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

    protected ShoppingFormInputDialog(Builder builder) {
        super(builder.context, R.style.Dialog_NoTitle);
        initData();
        initView(builder.context);
        bindDataToViews(builder.data);
        setWindowProperty();
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        rootView = inflater.inflate(R.layout.shopping_form_input_dialog, null);
        View alphaBackground = rootView.findViewById(R.id.alpha_background);
        alphaBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ImageView productImage = (ImageView) rootView.findViewById(R.id.product_image);
        Picasso.with(context)
                // TODO: 2016-8-18
                .load("todo")
                .error(R.drawable.default_test_image)
                .resize(ImageUtils.dip2px(context, 64), ImageUtils.dip2px(context, 64))
                .into(productImage);
        View selectAll = rootView.findViewById(R.id.select_all);
        selectAll.setVisibility(View.GONE);
        View totalPriceLayout = rootView.findViewById(R.id.total_price_layout);
        totalPriceLayout.setVisibility(View.VISIBLE);
        totalPrice = (TextView) rootView.findViewById(R.id.tv_total_price);
        totalPrice.setText("500");
        TagView payTypeTagView = (TagView) rootView.findViewById(R.id.pay_type);
        TagViewAdapter tagViewAdapter = new TagViewAdapter(context, dataList);
        payTypeTagView.setLayoutManager(new GridLayoutManager(context, 2));
        payTypeTagView.setAdapter(tagViewAdapter);
    }


    // TODO: 2016-8-12
    private void initData() {
        dataList = new ArrayList<String>();
        dataList.add("支付宝");
        dataList.add("微信");
        dataList.add("银联");
        dataList.add("财付通");
    }

    private void bindDataToViews(int data) {
        TextView test = (TextView) rootView.findViewById(R.id.tv_unit_price);
        test.setText(data + "  测试 ");
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

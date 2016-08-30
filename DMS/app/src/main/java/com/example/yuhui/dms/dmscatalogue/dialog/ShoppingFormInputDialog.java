package com.example.yuhui.dms.dmscatalogue.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuhui.dms.ImageUtils;
import com.example.yuhui.dms.R;
import com.example.yuhui.dms.Utils;
import com.example.yuhui.dms.dmscatalogue.DisplayMode;
import com.example.yuhui.dms.dmscatalogue.transformation.CornerTransformation;
import com.example.yuhui.dms.dmscatalogue.view.AmountEditView;
import com.example.yuhui.dms.dmscatalogue.view.TextTagView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuhui on 2016-8-12.
 */
public class ShoppingFormInputDialog extends Dialog {
    private View rootView;
    private TextView totalPriceView;
    private List dataList;
    private float totalPrice;
    private float unitPrice;


    public static class Builder {
        private final Context context;
        private int data;

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
        FrameLayout imageLayout = (FrameLayout) rootView.findViewById(R.id.image_layout);
        if (DisplayMode.isDisplayImage()) {
            View alphaBackground = rootView.findViewById(R.id.alpha_background);
            alphaBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            final ImageView productImage = (ImageView) rootView.findViewById(R.id.product_image);
            Picasso.with(context)
                    // TODO: 2016-8-18
                    .load("http://pic25.nipic.com/20121201/11501528_124108168130_2.jpg")
                    .error(R.drawable.default_test_image)
                    .resize(ImageUtils.dip2px(context, 64), ImageUtils.dip2px(context, 64))
                    .centerCrop()
                    .transform(new CornerTransformation())
                    .into(productImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Drawable drawable = getContext().getResources().getDrawable(R.drawable.default_test_image);
                            Bitmap bitmap = ImageUtils.drawableToBitmap(drawable);
                            productImage.setImageBitmap(ImageUtils.getRoundedCornerBitmap(bitmap, 5f));
                        }
                    });
        } else {
            imageLayout.setVisibility(View.GONE);
        }
        CheckBox selectAll = (CheckBox) rootView.findViewById(R.id.select_all);
        selectAll.setVisibility(View.GONE);
        View totalPriceLayout = rootView.findViewById(R.id.total_price_layout);
        totalPriceLayout.setVisibility(View.VISIBLE);
        totalPriceView = (TextView) rootView.findViewById(R.id.tv_total_price);
        unitPrice = 50;
        totalPrice = unitPrice;
        totalPriceView.setText(Utils.formatNumber(totalPrice));
        TextView unitPriceView = (TextView) rootView.findViewById(R.id.tv_unit_price);
        unitPriceView.setText(unitPrice + "");
        AmountEditView amountEditView = (AmountEditView) rootView.findViewById(R.id.amount_editor);
        amountEditView.setAmount(1);
        amountEditView.setOnAmountChangeListener(new AmountEditView.OnAmountChangeListener() {
            @Override
            public void onAmountChanged(int amount) {
                totalPrice = unitPrice * amount;
                totalPriceView.setText(Utils.formatNumber(totalPrice));
            }
        });
        TextView supplierView = (TextView) rootView.findViewById(R.id.tv_dialog_supplier);
        supplierView.setText(String.format(context.getString(R.string.supplier), "广州玄武供应商"));

        TextTagView payTypeTextTagView = (TextTagView) rootView.findViewById(R.id.ttv_pay_type);
        int column = 4;
        payTypeTextTagView.setLayoutManager(new GridLayoutManager(context, column == 4 ? 2 : 3));
        payTypeTextTagView.setData(dataList);
        Button leftButton = (Button) rootView.findViewById(R.id.btn_left);
        leftButton.setVisibility(View.GONE);
        Button rightButton = (Button) rootView.findViewById(R.id.btn_right);
        rightButton.setText(R.string.add_to_shopping_car);
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

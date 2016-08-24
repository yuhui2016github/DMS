package com.example.yuhui.dms.dmscatalogue.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yuhui.dms.ImageUtils;
import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.bean.ProductBean;
import com.example.yuhui.dms.dmscatalogue.view.AmountEditView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yuhui on 2016-8-22.
 */
public class PurchaseOrderListAdapter extends RecyclerView.Adapter<PurchaseOrderListAdapter.PurchaseOrderViewHolder> {
    private Context context;
    private List dataLsit;
    private boolean showDetail;
    private OrderOnclickListener orderOnclickListener;
    private List<Object> productDetailList;

    public PurchaseOrderListAdapter(Context context, List dataLsit) {
        this.context = context;
        this.dataLsit = dataLsit;
    }

    public void setShowDetail(boolean showDetail) {
        this.showDetail = showDetail;
    }

    public void setProductDetailList(List<Object> productDetailList) {
        this.productDetailList = productDetailList;
    }

    public void setOrderOnclickListener(OrderOnclickListener orderOnclickListener) {
        this.orderOnclickListener = orderOnclickListener;
    }

    @Override
    public PurchaseOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.purchase_order_list_item, parent, false);
        return new PurchaseOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PurchaseOrderViewHolder holder, final int position) {
        if (!showDetail) {
            holder.productDetailLayout.setVisibility(View.GONE);
            holder.orderDescriptionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderOnclickListener.onClick(position, position);
                }
            });
        } else {
            holder.orderDescriptionLayout.setVisibility(View.GONE);
            final ProductBean productBean = (ProductBean) productDetailList.get(position);
            if (productBean.isValid()) {
                holder.childTag.setVisibility(View.GONE);
            }
            Picasso.with(context)
                    .load(productBean.getImageUri())
                    .error(R.drawable.catalogue_shopping_bus)
                    .resize(ImageUtils.dip2px(context, 100), ImageUtils.dip2px(context, 100))
                    .into(holder.productImage);
            holder.productName.setText(productBean.getName());
            holder.giftDetail.setText(productBean.getGiftDetail());
            holder.unitPrice.setText(productBean.getUnitPrice());
            holder.amount.setText("  x " + productBean.getAmount());
            holder.payType.setText(productBean.getPayType() + "");
            holder.amountEditor.setOnAmountChangeListener(new AmountEditView.OnAmountChangeListener() {
                @Override
                public void onAmountChanged(int amount) {
                    productBean.setAmount(amount);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return dataLsit.size();
    }

    class PurchaseOrderViewHolder extends RecyclerView.ViewHolder {
        LinearLayout productDetailLayout;
        RelativeLayout orderDescriptionLayout;

        CheckBox childSelectBox;
        TextView childTag;
        ImageView productImage;
        TextView productName;
        TextView giftDetail;
        TextView unitPrice;
        TextView amount;
        TextView payType;
        AmountEditView amountEditor;

        public PurchaseOrderViewHolder(View itemView) {
            super(itemView);
            orderDescriptionLayout = (RelativeLayout) itemView.findViewById(R.id.order_description_layout);


            productDetailLayout = (LinearLayout) itemView.findViewById(R.id.shopping_car_child_item);
            childSelectBox = (CheckBox) itemView.findViewById(R.id.child_select);
            childTag = (TextView) itemView.findViewById(R.id.child_tag);
            productImage = (ImageView) itemView.findViewById(R.id.product_image);
            productName = (TextView) itemView.findViewById(R.id.child_product_name);
            giftDetail = (TextView) itemView.findViewById(R.id.gift_detail);
            unitPrice = (TextView) itemView.findViewById(R.id.unit_price);
            amount = (TextView) itemView.findViewById(R.id.amount);
            payType = (TextView) itemView.findViewById(R.id.pay_type);
            amountEditor = (AmountEditView) itemView.findViewById(R.id.amount_editor);
        }
    }

    public interface OrderOnclickListener {
        void onClick(int position, Object data);
    }
}

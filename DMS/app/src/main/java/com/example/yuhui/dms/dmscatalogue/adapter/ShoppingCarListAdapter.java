package com.example.yuhui.dms.dmscatalogue.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuhui.dms.ImageUtils;
import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.bean.ProductBean;
import com.example.yuhui.dms.dmscatalogue.bean.StoreBean;
import com.example.yuhui.dms.dmscatalogue.view.AmountEditView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yuhui on 2016-8-16.
 */
public class ShoppingCarListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Object> groupMapList;
    private List<List<Object>> childMapList;

    public ShoppingCarListAdapter(Context context) {
        this.context = context;
    }

    public ShoppingCarListAdapter(Context context, List<Object> groupMapList, List<List<Object>> childMapList) {
        this.context = context;
        this.groupMapList = groupMapList;
        this.childMapList = childMapList;
    }

    @Override
    public int getGroupCount() {
        return groupMapList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childMapList.get(groupPosition).size();
    }

    //获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        return groupMapList.get(groupPosition);
    }

    //得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childMapList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shopping_car_group_item, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.groupSelectBox = (CheckBox) convertView.findViewById(R.id.group_select);
            groupViewHolder.groupTag = (TextView) convertView.findViewById(R.id.group_tag);
            groupViewHolder.supplierName = (TextView) convertView.findViewById(R.id.supplier_name);
            groupViewHolder.totalPrice = (TextView) convertView.findViewById(R.id.tv_group_price);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        StoreBean storeBean = (StoreBean) groupMapList.get(groupPosition);
        if (storeBean.isValid()) {
            groupViewHolder.groupTag.setVisibility(View.GONE);
        }
        groupViewHolder.supplierName.setText(storeBean.getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shopping_car_child_item, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.childSelectBox = (CheckBox) convertView.findViewById(R.id.child_select);
            childViewHolder.childTag = (TextView) convertView.findViewById(R.id.child_tag);
            childViewHolder.productImage = (ImageView) convertView.findViewById(R.id.product_image);
            childViewHolder.productName = (TextView) convertView.findViewById(R.id.child_product_name);
            childViewHolder.giftDetail = (TextView) convertView.findViewById(R.id.gift_detail);
            childViewHolder.unitPrice = (TextView) convertView.findViewById(R.id.unit_price);
            childViewHolder.amount = (TextView) convertView.findViewById(R.id.amount);
            childViewHolder.payType = (TextView) convertView.findViewById(R.id.pay_type);
            childViewHolder.amountEditor = (AmountEditView) convertView.findViewById(R.id.amount_editor);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        final ProductBean productBean = (ProductBean) childMapList.get(groupPosition).get(childPosition);
        if (productBean.isValid()) {
            childViewHolder.childTag.setVisibility(View.GONE);
        }
        Picasso.with(context)
                .load(productBean.getImageUri())
                .error(R.drawable.catalogue_shopping_bus)
                .resize(ImageUtils.dip2px(context, 100), ImageUtils.dip2px(context, 100))
                .into(childViewHolder.productImage);
        childViewHolder.productName.setText(productBean.getName());
        childViewHolder.giftDetail.setText(productBean.getGiftDetail());
        childViewHolder.unitPrice.setText(productBean.getUnitPrice());
        childViewHolder.amount.setText("  x " + productBean.getAmount());
        childViewHolder.payType.setText(productBean.getPayType() + "");
        childViewHolder.amountEditor.setOnAmountChangeListener(new AmountEditView.OnAmountChangeListener() {
            @Override
            public void onAmountChanged(int amount) {
                Log.i("yuhui", "amount" + amount);
                productBean.setAmount(amount);
            }
        });
        Log.i("yuhui", "group  " + groupPosition + "  child position  " + childPosition
                + "   productBean.amount " + productBean.getAmount());
        childViewHolder.amountEditor.setAmount(productBean.getAmount());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void setGroupMapList(List<Object> groupMapList) {
        this.groupMapList = groupMapList;
        notifyDataSetChanged();
    }

    public void setChildMapList(List<List<Object>> childMapList) {
        this.childMapList = childMapList;
        notifyDataSetChanged();
    }

    class GroupViewHolder {
        CheckBox groupSelectBox;
        TextView groupTag;
        TextView supplierName;
        TextView totalPrice;
    }

    class ChildViewHolder {
        CheckBox childSelectBox;
        TextView childTag;
        ImageView productImage;
        TextView productName;
        TextView giftDetail;
        TextView unitPrice;
        TextView amount;
        TextView payType;
        AmountEditView amountEditor;
    }
}

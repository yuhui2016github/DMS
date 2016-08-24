package com.example.yuhui.dms.dmscatalogue.adapter;

import android.content.Context;
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
    private List<Object> groupList;
    private List<List<Object>> childList;
    private boolean sholdShowCheckBox;
    private boolean groupSupressEvent = false;
    private boolean childSupressEvent = false;
    private GlobalCheckedChangeListener globalCheckedChangeListener;


    public void setGroupList(List<Object> groupList) {
        this.groupList = groupList;
        notifyDataSetChanged();
    }

    public void setChildList(List<List<Object>> childList) {
        this.childList = childList;
        notifyDataSetChanged();
    }

    public void setSholdShowCheckBox(boolean sholdShowCheckBox) {
        this.sholdShowCheckBox = sholdShowCheckBox;
    }

    public void setGlobalCheckedChangeListener(GlobalCheckedChangeListener globalCheckedChangeListener) {
        this.globalCheckedChangeListener = globalCheckedChangeListener;
    }

    public ShoppingCarListAdapter(Context context) {
        this.context = context;
    }

    public ShoppingCarListAdapter(Context context, List<Object> groupList, List<List<Object>> childList) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    //获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    //得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
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
        final StoreBean storeBean = (StoreBean) groupList.get(groupPosition);
        if (storeBean.isValid()) {
            groupViewHolder.groupTag.setVisibility(View.GONE);
        }
        if (sholdShowCheckBox) {
            groupViewHolder.groupSelectBox.setVisibility(View.VISIBLE);
        } else {
            groupViewHolder.groupSelectBox.setVisibility(View.GONE);
        }
        final CheckBox groupCheckBox = groupViewHolder.groupSelectBox;
        groupCheckBox.setChecked(storeBean.isChecked());
        groupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = groupCheckBox.isChecked();
                storeBean.setIsChecked(isChecked);
                for (Object productBean : childList.get(groupPosition)) {
                    ((ProductBean) productBean).setIsChecked(isChecked);
                }
                notifyDataSetChanged();
                updateGlobalCheckStatus(isChecked);
            }
        });
        groupViewHolder.supplierName.setText(storeBean.getName());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
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
        final ProductBean productBean = (ProductBean) childList.get(groupPosition).get(childPosition);
        if (productBean.isValid()) {
            childViewHolder.childTag.setVisibility(View.GONE);
        }
        if (sholdShowCheckBox) {
            childViewHolder.childSelectBox.setVisibility(View.VISIBLE);
        } else {
            childViewHolder.childSelectBox.setVisibility(View.GONE);
        }
        final CheckBox childCheckBox = childViewHolder.childSelectBox;
        childCheckBox.setChecked(productBean.isChecked());
        childCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = childCheckBox.isChecked();
                productBean.setIsChecked(isChecked);
                if (isChecked) {
                    for (Object productBean : childList.get(groupPosition)) {
                        if (!((ProductBean) productBean).isChecked()) {
                            return;
                        }
                    }
                } else {
                    globalCheckedChangeListener.onGlobalCheckedChanged(isChecked);
                }
                ((StoreBean) groupList.get(groupPosition)).setIsChecked(isChecked);
                notifyDataSetChanged();
                updateGlobalCheckStatus(isChecked);
            }
        });

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
                productBean.setAmount(amount);
            }
        });
        childViewHolder.amountEditor.setAmount(productBean.getAmount());
        return convertView;
    }

    private void updateGlobalCheckStatus(boolean isChecked) {
        //更新footView的checkbox状态
        if (isChecked) {
            for (Object storeBean : groupList) {
                if (!((StoreBean) storeBean).isChecked()) {
                    return;
                }
            }
        }
        globalCheckedChangeListener.onGlobalCheckedChanged(isChecked);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public interface GlobalCheckedChangeListener {
        public void onGlobalCheckedChanged(boolean isChecked);
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

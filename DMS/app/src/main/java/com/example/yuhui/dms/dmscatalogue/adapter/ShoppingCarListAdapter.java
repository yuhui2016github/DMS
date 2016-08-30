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
import com.example.yuhui.dms.Utils;
import com.example.yuhui.dms.dmscatalogue.DisplayMode;
import com.example.yuhui.dms.dmscatalogue.bean.ProductBean;
import com.example.yuhui.dms.dmscatalogue.bean.StoreBean;
import com.example.yuhui.dms.dmscatalogue.view.AmountEditView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by yuhui on 2016-8-16.
 */
public class ShoppingCarListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Object> groupList;
    private List<List<Object>> childList_List;
    private GlobalCheckedChangeListener globalCheckedChangeListener;
    private OnProductCheckedChangeListener onProductCheckedChangeListener;
    //map储存group的ViewHolder，方便childView的AmountEditor可以动态更新groupView
    private Map<Integer, GroupViewHolder> groupMap;


    public void setGroupList(List<Object> groupList) {
        this.groupList = groupList;
        notifyDataSetChanged();
    }

    public void setChildList_List(List<List<Object>> childList_List) {
        this.childList_List = childList_List;
        notifyDataSetChanged();
    }

    public void setOnProductCheckedChangeListener(OnProductCheckedChangeListener onProductCheckedChangeListener) {
        this.onProductCheckedChangeListener = onProductCheckedChangeListener;
    }

    public void setGlobalCheckedChangeListener(GlobalCheckedChangeListener globalCheckedChangeListener) {
        this.globalCheckedChangeListener = globalCheckedChangeListener;
    }

    public ShoppingCarListAdapter(Context context, List<Object> groupList, List<List<Object>> childList_List) {
        this.context = context;
        this.groupList = groupList;
        this.childList_List = childList_List;
        groupMap = new WeakHashMap<>();
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList_List.get(groupPosition).size();
    }

    //获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    //得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList_List.get(groupPosition).get(childPosition);
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
        GroupViewHolder groupViewHolder;
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
        float groupTotalPrice = calculateGroupTotalPrice(groupPosition);
        groupViewHolder.totalPrice.setText(Utils.formatNumber(groupTotalPrice));
        final CheckBox groupCheckBox = groupViewHolder.groupSelectBox;
        groupCheckBox.setChecked(storeBean.isChecked());
        groupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = groupCheckBox.isChecked();
                storeBean.setIsChecked(isChecked);
                for (Object productBean : childList_List.get(groupPosition)) {
                    ((ProductBean) productBean).setIsChecked(isChecked);
                }
                notifyDataSetChanged();
                updateGlobalCheckStatus(isChecked);
            }
        });
        groupViewHolder.supplierName.setText(storeBean.getName());
        groupMap.put(groupPosition, groupViewHolder);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shopping_car_child_item, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.childSelectBox = (CheckBox) convertView.findViewById(R.id.child_select);
            childViewHolder.childTag = (TextView) convertView.findViewById(R.id.child_tag);
            childViewHolder.productImage = (ImageView) convertView.findViewById(R.id.product_image);
            childViewHolder.productName = (TextView) convertView.findViewById(R.id.child_product_name);
            childViewHolder.giftDetail = (TextView) convertView.findViewById(R.id.gift_detail);
            childViewHolder.unitPrice = (TextView) convertView.findViewById(R.id.tv_unit_price);
            childViewHolder.amount = (TextView) convertView.findViewById(R.id.amount);
            childViewHolder.payType = (TextView) convertView.findViewById(R.id.tv_pay_type);
            childViewHolder.amountEditor = (AmountEditView) convertView.findViewById(R.id.amount_editor);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        final ProductBean productBean = (ProductBean) childList_List.get(groupPosition).get(childPosition);
        if (productBean.isValid()) {
            childViewHolder.childTag.setVisibility(View.GONE);
        }

        final CheckBox childCheckBox = childViewHolder.childSelectBox;
        childCheckBox.setChecked(productBean.isChecked());
        childCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = childCheckBox.isChecked();
                productBean.setIsChecked(isChecked);
                if (isChecked) {
                    for (Object productBean : childList_List.get(groupPosition)) {
                        if (!((ProductBean) productBean).isChecked()) {
                            notifyDataSetChanged();
                            return;
                        }
                    }
                } else {
                    globalCheckedChangeListener.onGlobalCheckedChanged(false);
                }
                ((StoreBean) groupList.get(groupPosition)).setIsChecked(isChecked);
                notifyDataSetChanged();
                updateGlobalCheckStatus(isChecked);
            }
        });
        if (DisplayMode.isDisplayImage()) {
            Picasso.with(context)
                    .load(productBean.getImageUri())
                    .error(R.drawable.catalogue_shopping_bus)
                    .resize(ImageUtils.dip2px(context, 100), ImageUtils.dip2px(context, 100))
                    .into(childViewHolder.productImage);
        } else {
            childViewHolder.productImage.setVisibility(View.GONE);
        }
        childViewHolder.productName.setText(productBean.getName());
        childViewHolder.giftDetail.setText(productBean.getGiftDetail());
        childViewHolder.unitPrice.setText(productBean.getUnitPrice() + "");
        childViewHolder.amount.setVisibility(View.GONE);
        childViewHolder.payType.setText(productBean.getPayType() + "");
        childViewHolder.amountEditor.setOnAmountChangeListener(new AmountEditView.OnAmountChangeListener() {
            @Override
            public void onAmountChanged(int amount) {
                productBean.setAmount(amount);
                GroupViewHolder groupViewHolder = groupMap.get(groupPosition);
                float groupTotalPrice = calculateGroupTotalPrice(groupPosition);
                groupViewHolder.totalPrice.setText(Utils.formatNumber(groupTotalPrice));
                dealPrice();
            }
        });
        childViewHolder.amountEditor.setAmount(productBean.getAmount());
        return convertView;
    }

    public float calculateGroupTotalPrice(int groupPosition) {
        float groupTotalPrice = 0;
        for (Object child : childList_List.get(groupPosition)) {
            ProductBean productBean = (ProductBean) child;
            if (productBean.isChecked()) {
                groupTotalPrice += productBean.getAmount() * productBean.getUnitPrice();
            }
        }
        return groupTotalPrice;
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

    public void removeProducts() {
        for (int i = groupList.size() - 1; i >= 0; i--) { //倒过来遍历remove
            StoreBean storeBean = (StoreBean) groupList.get(i);
            if (storeBean.isChecked()) {
                groupList.remove(i);
                childList_List.remove(i);
            } else {
                List childList = childList_List.get(i);
                for (int j = childList.size() - 1; j >= 0; j--) {
                    ProductBean productBean = (ProductBean) childList.get(j);
                    if (productBean.isChecked()) {
                        childList.remove(j);
                    }
                }
            }
        }
        notifyDataSetChanged();
        dealPrice();
    }

    public void removeInvalidProducts() {
        for (int i = groupList.size() - 1; i >= 0; i--) { //倒过来遍历remove
            StoreBean storeBean = (StoreBean) groupList.get(i);
            if (!storeBean.isValid()) {
                groupList.remove(i);
                childList_List.remove(i);
            } else {
                List childList = childList_List.get(i);
                for (int j = childList.size() - 1; j >= 0; j--) {
                    ProductBean productBean = (ProductBean) childList.get(j);
                    if (!productBean.isValid()) {
                        childList.remove(j);
                    }
                }
            }
        }
        notifyDataSetChanged();
        dealPrice();
    }

    public void dealPrice() {
        int totalCount = 0;
        float totalPrice = 0f;
        for (int i = 0; i < groupList.size(); i++) {
            List<Object> childMapList = childList_List.get(i);
            for (int j = 0; j < childMapList.size(); j++) {
                ProductBean productBean = (ProductBean) childMapList.get(j);
                int count = productBean.getAmount();
                float discountPrice = productBean.getUnitPrice();
                if (productBean.isChecked()) {
                    totalCount++;//单品多数量只记1
                    totalPrice += discountPrice * count;
                }

            }
        }

        onProductCheckedChangeListener.onProductCheckedChange(totalCount, totalPrice);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public interface OnProductCheckedChangeListener {
        void onProductCheckedChange(int totalCount, float totalPrice);
    }


    public interface GlobalCheckedChangeListener {
        void onGlobalCheckedChanged(boolean isChecked);
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

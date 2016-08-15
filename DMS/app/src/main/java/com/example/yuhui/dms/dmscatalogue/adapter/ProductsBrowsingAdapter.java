package com.example.yuhui.dms.dmscatalogue.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yuhui.dms.ImageUtils;
import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.view.CatalogueTagView;
import com.example.yuhui.dms.dmscatalogue.view.ShoppingFormInputDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yuhui on 2016-8-10.
 */
public class ProductsBrowsingAdapter extends RecyclerView.Adapter<ProductsBrowsingAdapter.CommdityBrowsingViewHolder> {
    private Context context;
    private View rootView;
    private List<String> dataList;
    private ProductImageOnclickListener imageOnclickListener;

    public ProductsBrowsingAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setImageOnclickListener(ProductImageOnclickListener imageOnclickListener) {
        this.imageOnclickListener = imageOnclickListener;
    }

    @Override
    public CommdityBrowsingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = LayoutInflater.from(context).inflate(R.layout.products_browsing_item, parent, false);
        CommdityBrowsingViewHolder commdityBrowsingViewHolder = new CommdityBrowsingViewHolder(rootView);
        return commdityBrowsingViewHolder;
    }

    @Override
    public void onBindViewHolder(CommdityBrowsingViewHolder holder, int position) {
        setCheckBoxEvent(holder, position);
        setItemData(holder, position);
    }

    private void setCheckBoxEvent(final CommdityBrowsingViewHolder holder, final int position) {
        Drawable drawable;
        if (position % 3 == 0) {
            drawable = context.getResources()
                    .getDrawable(R.drawable.catalogue_shopping_bus_press);
        } else {
            drawable = context.getResources()
                    .getDrawable(R.drawable.catalogue_shopping_bus);
        }
        holder.mShoppingCheckBox
                .setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

        holder.mShoppingCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这一步必须要做,否则不会显示.
                new ShoppingFormInputDialog.Builder(context).setData(7).show();
            }
        });
    }

    private void setItemData(CommdityBrowsingViewHolder holder, final int position) {
        //设置数据
        holder.mTVTitle.setText(dataList.get(position));
        holder.mTVPrice.setText("500");
        Picasso.with(context)
                .load("http://pic25.nipic.com/20121201/11501528_124108168130_2.jpg")
                .error(R.drawable.catalogue_shopping_bus)
                .resize(ImageUtils.dip2px(context, 187), ImageUtils.dip2px(context, 130))
                .into(holder.mImageView);

        if (true) {
            holder.mTagView.setVisibility(View.VISIBLE);
            if (position % 3 == 0) {
                holder.mTagView.setTagType(CatalogueTagView.TYPE_OUT_OF_STOCK);
            } else {
                holder.mTagView.setTagType(CatalogueTagView.TYPE_WITH_GIFT);
            }
        } else {
            holder.mTagView.setVisibility(View.GONE);
        }
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageOnclickListener.onClick(position, dataList.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.size();
        }
        return 0;
    }

    public static class CommdityBrowsingViewHolder extends RecyclerView.ViewHolder {

        public TextView mTVTitle;
        public ImageView mImageView;
        public TextView mTVPrice;
        public TextView mTVPriceIcon;
        public CheckBox mCollectCheckBox;
        public CheckBox mShoppingCheckBox;
        public RelativeLayout mCatalogueLayout;
        public CatalogueTagView mTagView;

        public CommdityBrowsingViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTVTitle = (TextView) itemView.findViewById(R.id.item_tv_title);
            mImageView = (ImageView) itemView.findViewById(R.id.item_iv_pic);
            mTVPrice = (TextView) itemView.findViewById(R.id.item_tv_price);
            mTVPriceIcon = (TextView) itemView.findViewById(R.id.item_tv_price_icon);
            mCollectCheckBox = (CheckBox) itemView.findViewById(R.id.item_cb_collection);
            mShoppingCheckBox = (CheckBox) itemView.findViewById(R.id.item_cb_shopping);
            mTagView = (CatalogueTagView) itemView.findViewById(R.id.item_catalogue_tag);
            mCatalogueLayout = (RelativeLayout) itemView.findViewById(R.id.item_catalogue_layout);
        }
    }

    public interface ProductImageOnclickListener {
        void onClick(int position, Object data);
    }
}

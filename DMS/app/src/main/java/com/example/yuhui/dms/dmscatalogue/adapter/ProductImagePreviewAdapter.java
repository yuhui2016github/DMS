package com.example.yuhui.dms.dmscatalogue.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yuhui.dms.R;

import java.util.List;

/**
 * Created by yuhui on 2016-8-22.
 */
public class ProductImagePreviewAdapter extends RecyclerView.Adapter<ProductImagePreviewAdapter.ProductPreviewImageHolder> {
    private Context context;
    private List<Integer> dataList;

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

    public ProductImagePreviewAdapter(Context context, List<Integer> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ProductPreviewImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.product_image_preview_item, parent, false);
        ProductPreviewImageHolder productPreviewImageHolder = new ProductPreviewImageHolder(view);
        return productPreviewImageHolder;
    }

    @Override
    public void onBindViewHolder(ProductPreviewImageHolder holder, int position) {
        holder.imageView.setImageResource(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ProductPreviewImageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ProductPreviewImageHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.products_preview_image);
        }
    }
}

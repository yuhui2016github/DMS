package com.example.yuhui.dms.dmscatalogue.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yuhui.dms.ImageUtils;
import com.example.yuhui.dms.R;

import java.util.List;

/**
 * Created by yuhui on 2016-8-18.
 */
public class TagViewAdapter extends RecyclerView.Adapter<TagViewAdapter.TagViewHolder> {
    private Context context;
    private List<String> dataList;
    private int selectedPosition = -1;

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public TagViewAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.tag_view_item, parent, false);
        TagViewHolder tagViewHolder = new TagViewHolder(itemView);
        return tagViewHolder;
    }

    @Override
    public void onBindViewHolder(TagViewHolder holder, final int position) {
        TextView textView = holder.itemTextView;
        textView.setText(dataList.get(position));
        if (selectedPosition == position) {
            textView.setBackground(context.getResources().getDrawable(R.drawable.bg_tag_item));
            textView.setTextColor(Color.WHITE);
        } else {
            textView.setBackground(null);
            textView.setTextColor(R.color.order_table_text_color);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    class TagViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTextView;

        public TagViewHolder(View itemView) {
            super(itemView);
            this.itemTextView = (TextView) itemView.findViewById(R.id.tag_item);
        }
    }
}

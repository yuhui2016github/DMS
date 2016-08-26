package com.example.yuhui.dms.dmscatalogue.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.interfaces.TagPopupSelectListener;

import java.util.List;

/**
 * Created by yuhui on 2016-8-26.
 */
public class PopTagListAdapter extends RecyclerView.Adapter<PopTagListAdapter.PopTagListViewHolder> {
    private List<String> dataList;
    private Context context;
    private TagPopupSelectListener tagPopupSelectListener;

    public PopTagListAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public void setTagPopupSelectListener(TagPopupSelectListener tagPopupSelectListener) {
        this.tagPopupSelectListener = tagPopupSelectListener;
    }

    @Override
    public PopTagListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_tag_listview_item, parent, false);
        return new PopTagListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PopTagListViewHolder holder, final int position) {
        holder.tvItemName.setText(dataList.get(position));
        holder.tvItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagPopupSelectListener.onSelectItem(dataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class PopTagListViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;

        public PopTagListViewHolder(View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.tv_item_name);
        }
    }
}

package com.example.yuhui.dms.dmscatalogue.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yuhui.dms.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by yuhui on 2016-8-18.
 */
public class TextTagViewAdapter extends RecyclerView.Adapter<TextTagViewAdapter.TextTagViewHolder> {
    public static final int TAG_STYLE_NONE = 0;
    public static final int TAG_STYLE_ICON = 1;

    @IntDef({TAG_STYLE_ICON, TAG_STYLE_NONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TextTagStyle {
    }

    private Context context;
    private List<String> dataList;
    private int selectedPosition = -1;
    private int tagStyle;
    private float textSize;
    private int widthSize;
    private int marginBottom;
    private int marginLeft;
    private int marginRight;
    private OnSelectedChangedListener onSelectedChangedListener;


    public void setOnSelectedChangedListener(OnSelectedChangedListener onSelectedChangedListener) {
        this.onSelectedChangedListener = onSelectedChangedListener;
    }

    public void setWidthSize(int widthSize) {
        this.widthSize = widthSize;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setTagStyle(@TextTagStyle int tagStyle) {
        this.tagStyle = tagStyle;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    public TextTagViewAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public TextTagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.text_tag_view_item, parent, false);
        return new TextTagViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TextTagViewHolder holder, final int position) {
        TextView textView = holder.itemTextView;
        ImageView icon = holder.itemSelectedIcon;
        RelativeLayout itemLayout = holder.itemLayout;

        textView.setText(dataList.get(position));
        textView.setTextSize(textSize);
        textView.setWidth(widthSize);
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) itemLayout.getLayoutParams();
        lp.setMargins(marginLeft, 0, marginRight, marginBottom);

        switch (tagStyle) {
            case TAG_STYLE_NONE:
                if (selectedPosition == position) {
                    textView.setBackground(context.getResources().getDrawable(R.drawable.bg_texttag_none_item));
                    textView.setTextColor(0xFFFFFFFF);
                } else {
                    textView.setBackground(null);
                    textView.setTextColor(0xFF333333);
                }
                break;
            case TAG_STYLE_ICON:
                if (selectedPosition == position) {
                    textView.setBackground(context.getResources().getDrawable(R.drawable.bg_texttag_icon_selected_item));
                    textView.setTextColor(0xFFFF5B00);
                    icon.setVisibility(View.VISIBLE);
                } else {
                    textView.setBackground(context.getResources().getDrawable(R.drawable.bg_texttag_icon_unselected_item));
                    textView.setTextColor(0xFF3F3F3F);
                    icon.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedPosition(position);
                if (onSelectedChangedListener != null) {
                    onSelectedChangedListener.onSelectedChanged(position);
                }
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

    class TextTagViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTextView;
        public ImageView itemSelectedIcon;
        public RelativeLayout itemLayout;

        public TextTagViewHolder(View itemView) {
            super(itemView);
            itemTextView = (TextView) itemView.findViewById(R.id.tag_item);
            itemSelectedIcon = (ImageView) itemView.findViewById(R.id.select_icon);
            itemLayout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
        }
    }

    public interface OnSelectedChangedListener {
        void onSelectedChanged(int position);
    }
}

package com.example.yuhui.dms.dmscatalogue.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.yuhui.dms.ImageUtils;
import com.example.yuhui.dms.R;
import com.example.yuhui.dms.dmscatalogue.adapter.TextTagViewAdapter;

import java.util.List;

/**
 * Created by yuhui on 2016-8-18.
 */
public class TextTagView extends RecyclerView {
    private TextTagViewAdapter textTagViewAdapter;
    private int tagStyle;
    private float textSize;
    private int widthSize;
    private int marginBottom;
    private int marginLeft;
    private int marginRight;


    public void setTagStyle(int tagStyle) {
        this.tagStyle = tagStyle;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setWidthSize(int widthSize) {
        this.widthSize = widthSize;
    }

    public TextTagView(Context context) {
        super(context);
    }

    public TextTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextTagView);
        tagStyle = typedArray.getInt(R.styleable.TextTagView_selectedStyle, 0);
        textSize = typedArray.getDimension(R.styleable.TextTagView_textSize, 15) / 3;
        widthSize = ImageUtils.dip2px(context, typedArray.getDimension(R.styleable.TextTagView_widthSize, 80)) / 2;
        marginBottom = ImageUtils.dip2px(context, typedArray.getDimension(R.styleable.TextTagView_marginBottom, 0)) / 2;
        marginLeft = ImageUtils.dip2px(context, typedArray.getDimension(R.styleable.TextTagView_marginLeft, 0)) / 2;
        marginRight = ImageUtils.dip2px(context, typedArray.getDimension(R.styleable.TextTagView_marginRight, 0)) / 2;
        typedArray.recycle();
    }

    public void setData(List dataList) {
        textTagViewAdapter = new TextTagViewAdapter(getContext(), dataList);
        textTagViewAdapter.setWidthSize(widthSize);
        textTagViewAdapter.setMarginBottom(marginBottom);
        textTagViewAdapter.setMarginLeft(marginLeft);
        textTagViewAdapter.setMarginRight(marginRight);
        textTagViewAdapter.setTagStyle(tagStyle);
        textTagViewAdapter.setTextSize(textSize);
        this.setAdapter(textTagViewAdapter);
    }

    public void setSelectedPosition(int position) {
        if (textTagViewAdapter != null) {
            textTagViewAdapter.setSelectedPosition(position);
        }
    }

    public void setOnSelectedChangedListener(TextTagViewAdapter.OnSelectedChangedListener onSelectedChangedListener) {
        if (textTagViewAdapter != null) {
            textTagViewAdapter.setOnSelectedChangedListener(onSelectedChangedListener);
        }
    }

}

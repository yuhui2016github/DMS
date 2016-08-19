package com.example.yuhui.dms.dmscatalogue.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.yuhui.dms.dmscatalogue.adapter.TagViewAdapter;

import java.util.List;

/**
 * Created by yuhui on 2016-8-18.
 */
public class TextTagView extends RecyclerView {
    private TagViewAdapter tagViewAdapter;
    private List dataList;

    public TextTagView(Context context) {
        super(context);
    }

    public TextTagView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public void setData(List dataList) {
        this.dataList = dataList;
        tagViewAdapter = new TagViewAdapter(getContext(), dataList);
        this.setAdapter(tagViewAdapter);
    }

}

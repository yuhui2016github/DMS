package com.example.yuhui.dms.dmscatalogue.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2016/5/29.
 *
 */
public class AdapterDataObserverImpl extends RecyclerView.AdapterDataObserver {
    private RecyclerView.Adapter mWrapAdapter;

    public AdapterDataObserverImpl(RecyclerView.Adapter wrapAdapter){
        this.mWrapAdapter = wrapAdapter;
    }

    @Override
    public void onChanged() {
        mWrapAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
    }


    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
    }
}

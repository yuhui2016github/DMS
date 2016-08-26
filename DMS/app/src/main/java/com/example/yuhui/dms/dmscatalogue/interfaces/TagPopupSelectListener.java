package com.example.yuhui.dms.dmscatalogue.interfaces;

/**
 * Created by yuhui on 2016-8-26.
 */
public interface TagPopupSelectListener {
    void onDateChanged(int year, int monthOfYear, int dayOfMonth);

    void onCancel();

    void onConfirm(int year, int monthOfYear, int dayOfMonth);

    void onSelectItem(String item);
}

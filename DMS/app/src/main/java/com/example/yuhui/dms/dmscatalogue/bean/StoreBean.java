package com.example.yuhui.dms.dmscatalogue.bean;

/**
 * Created by yuhui on 2016-8-17.
 */
public class StoreBean {
    private String id;
    private boolean isValid;
    private String name;
    private boolean isChecked = true;

    public StoreBean() {
    }

    public StoreBean(String id, boolean isValid, String name) {
        this.id = id;
        this.isValid = isValid;
        this.name = name;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}

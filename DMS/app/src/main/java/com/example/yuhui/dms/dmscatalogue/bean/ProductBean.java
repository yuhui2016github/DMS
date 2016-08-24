package com.example.yuhui.dms.dmscatalogue.bean;

/**
 * Created by yuhui on 2016-8-17.
 */
public class ProductBean {
    private String id;
    private boolean isValid;
    private String name;
    private String imageUri = "";
    private String giftDetail = "";
    private String unitPrice = "";
    private int amount;
    private int payType;
    private boolean isChecked = true;

    public ProductBean() {
    }

    public ProductBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductBean(String id, boolean isValid, String name, String imageUri, String giftDetail, String unitPrice, int amount, int payType) {
        this.id = id;
        this.isValid = isValid;
        this.name = name;
        this.imageUri = imageUri;
        this.giftDetail = giftDetail;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.payType = payType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getGiftDetail() {
        return giftDetail;
    }

    public void setGiftDetail(String giftDetail) {
        this.giftDetail = giftDetail;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}

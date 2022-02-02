package com.samansepahvand.calculateexpensesnew.business.metamodel;

public class AutoPriceModel {


    public AutoPriceModel(String bankName, String priceTitle, String price) {
        BankName = bankName;
        this.priceTitle = priceTitle;
        this.price = price;
    }

    private String  BankName ;
    private String  priceTitle ;
    private String  price ;


    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getPriceTitle() {
        return priceTitle;
    }

    public void setPriceTitle(String priceTitle) {
        this.priceTitle = priceTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}

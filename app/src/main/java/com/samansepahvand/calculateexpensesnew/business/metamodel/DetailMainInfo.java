package com.samansepahvand.calculateexpensesnew.business.metamodel;

public class DetailMainInfo {


    private String totalPrice;
    private String maxInvoicePrice;
    private String lastInvoiceDate;
    private String currentDate;
    private String invoiceCount;


    public DetailMainInfo() {
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setMaxInvoicePrice(String maxInvoicePrice) {
        this.maxInvoicePrice = maxInvoicePrice;
    }

    public void setLastInvoiceDate(String lastInvoiceDate) {
        this.lastInvoiceDate = lastInvoiceDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public void setInvoiceCount(String invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getMaxInvoicePrice() {
        return maxInvoicePrice;
    }

    public String getLastInvoiceDate() {
        return lastInvoiceDate;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public String getInvoiceCount() {
        return invoiceCount;
    }
}

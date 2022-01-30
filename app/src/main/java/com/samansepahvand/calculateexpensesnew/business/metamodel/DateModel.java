package com.samansepahvand.calculateexpensesnew.business.metamodel;

public class DateModel {

    private  int fromDate;
    private   int toDate;

    private  String  fromDateFarsi;
    private   String toDateFarsi;

    public DateModel(int fromDate, int toDate, String fromDateFarsi, String toDateFarsi) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.fromDateFarsi = fromDateFarsi;
        this.toDateFarsi = toDateFarsi;
    }

    public DateModel() {

    }

    public String getFromDateFarsi() {
        return fromDateFarsi;
    }

    public void setFromDateFarsi(String fromDateFarsi) {
        this.fromDateFarsi = fromDateFarsi;
    }

    public String getToDateFarsi() {
        return toDateFarsi;
    }

    public void setToDateFarsi(String toDateFarsi) {
        this.toDateFarsi = toDateFarsi;
    }

    public int getFromDate() {
        return fromDate;
    }

    public void setFromDate(int fromDate) {
        this.fromDate = fromDate;
    }

    public int getToDate() {
        return toDate;
    }

    public void setToDate(int toDate) {
        this.toDate = toDate;
    }
}

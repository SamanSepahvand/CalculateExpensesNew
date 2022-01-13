package com.samansepahvand.calculateexpensesnew.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;


@Table(name = Info.TABLE_NAME)
public class Info extends Model implements Serializable {

    public static final String TABLE_NAME = "Info";

    public static final String TITLE = "title";
    public static final String PRICE = "price";
    public static final String DATE = "date";

    public static final String DATE_FARSI = "farsiDate";
    public static final String DATE_ENGLISH= "englishDate";
    public static final String DATE_ESTIMATE= "estimateDate";


    public static final String PRICE_TYPE= "priceType";
    public static final String CreationDate = "CreationDate";
    public static final String CreatorUserId = "CreatorUserId";







    @Column(name = TITLE)
    String  title;

    @Column(name = PRICE)
    int price;

    @Column(name = DATE)
    String  date;

    @Column(name = DATE_FARSI)
    String  farsiDate;

    @Column(name = DATE_ENGLISH)
    String  englishDate;


    @Column(name = DATE_ESTIMATE)
    String  estimateDate;


    @Column(name = PRICE_TYPE)
    int  priceType;


    @Column(name = CreatorUserId)
    int  creatorUserId;

    @Column(name = CreationDate)
    String  creationDate;


    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public int getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(int creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(String estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getFarsiDate() {
        return farsiDate;
    }

    public void setFarsiDate(String farsiDate) {
        this.farsiDate = farsiDate;
    }

    public String getEnglishDate() {
        return englishDate;
    }

    public void setEnglishDate(String englishDate) {
        this.englishDate = englishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }




}
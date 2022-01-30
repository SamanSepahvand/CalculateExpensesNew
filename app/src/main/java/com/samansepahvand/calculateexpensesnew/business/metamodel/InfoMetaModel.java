package com.samansepahvand.calculateexpensesnew.business.metamodel;

import static com.samansepahvand.calculateexpensesnew.db.Info.*;
import static com.samansepahvand.calculateexpensesnew.db.PriceType.*;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.samansepahvand.calculateexpensesnew.db.PriceType;

import java.io.Serializable;


public class InfoMetaModel extends Model implements Serializable {




    @Column(name = PriceTypeName)
    String  priceTypeName;

    @Column(name = PriceTypeItemName)
    String  priceTypeItemName;

    @Column(name = PriceTypeId)
    int  priceTypeId;

    @Column(name = PriceTypeItemId)
    int  priceTypeItemId;



    @Column(name = TITLE)
    String  title;

    @Column(name = PRICE)
    int price;



    @Column(name = DATE_FARSI)
    String  farsiDate;

    @Column(name = DATE_ENGLISH)
    String  englishDate;


    @Column(name = DATE_ESTIMATE)
    String  estimateDate;


    @Column(name = CreatorUserId)
    int  creatorUserId;

    @Column(name = CreationDate)
    String  creationDate;


    public int getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(int priceTypeId) {
        this.priceTypeId = priceTypeId;
    }

    public int getPriceTypeItemId() {
        return priceTypeItemId;
    }

    public void setPriceTypeItemId(int priceTypeItemId) {
        this.priceTypeItemId = priceTypeItemId;
    }

    public String getPriceTypeName() {
        return priceTypeName;
    }

    public void setPriceTypeName(String priceTypeName) {
        this.priceTypeName = priceTypeName;
    }

    public String getPriceTypeItemName() {
        return priceTypeItemName;
    }

    public void setPriceTypeItemName(String priceTypeItemName) {
        this.priceTypeItemName = priceTypeItemName;
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



}
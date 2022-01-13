package com.samansepahvand.calculateexpensesnew.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;


@Table(name = PriceType.TABLE_NAME)
public class PriceType extends Model implements Serializable {

    public static final String TABLE_NAME = "PriceType";

    public static final String PriceTypeId = "PriceTypeId"; // header
    public static final String PriceTypeItemId = "PriceTypeItemId"; // itemId
    public static final String PriceTypeName = "PriceTypeName";

    public static final String PriceCreationDate = "PriceCreationDate";
    public static final String PriceCreatorUserId = "PriceCreatorUserId";


    @Column(name = PriceTypeId)
    String  priceTypeId;

    @Column(name = PriceTypeItemId)
    int priceTypeItemId;

    @Column(name = PriceTypeName)
    String  priceTypeName;

    @Column(name = PriceCreationDate)
    String  priceCreationDate;

    @Column(name = PriceCreatorUserId)
    int  priceCreatorUserId;


    public String getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(String priceTypeId) {
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

    public String getPriceCreationDate() {
        return priceCreationDate;
    }

    public void setPriceCreationDate(String priceCreationDate) {
        this.priceCreationDate = priceCreationDate;
    }

    public int getPriceCreatorUserId() {
        return priceCreatorUserId;
    }

    public void setPriceCreatorUserId(int priceCreatorUserId) {
        this.priceCreatorUserId = priceCreatorUserId;
    }
}
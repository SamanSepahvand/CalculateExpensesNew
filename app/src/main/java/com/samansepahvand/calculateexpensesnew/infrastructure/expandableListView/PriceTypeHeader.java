package com.samansepahvand.calculateexpensesnew.infrastructure.expandableListView;

import com.samansepahvand.calculateexpensesnew.db.PriceType;

import java.util.ArrayList;
import java.util.List;

public class PriceTypeHeader {

    public  int priceTypeId;
    public String priceTypeName;
    public  int pic;

    public final List<PriceType> priceTypeList = new ArrayList<PriceType>();

    public PriceTypeHeader(int priceTypeId, String priceTypeName, int pic) {
        this.priceTypeId = priceTypeId;
        this.priceTypeName = priceTypeName;
        this.pic = pic;
    }
}

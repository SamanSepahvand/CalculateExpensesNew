package com.samansepahvand.calculateexpensesnew.business.repository;

import android.content.Context;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.samansepahvand.calculateexpensesnew.business.metamodel.DetailMainInfo;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.db.Info;
import com.samansepahvand.calculateexpensesnew.db.PriceType;
import com.samansepahvand.calculateexpensesnew.infrastructure.Utility;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class PriceTypeRepository {

    private static final String TAG = "PriceTypeRepository";
    private static PriceTypeRepository priceTypeRepository = null;
    private Context context;

    private PriceTypeRepository() {

    }


    public static PriceTypeRepository getInstance() {

        if (priceTypeRepository == null) {

            synchronized (PriceTypeRepository.class) {
                if (priceTypeRepository == null) {
                    priceTypeRepository = new PriceTypeRepository();
                }
            }
        }
        return priceTypeRepository;
    }


    public OperationResult AddPriceType(PriceType priceType) {
        try {

           PriceType priceType1=  new Select().from(PriceType.class).where(PriceType.PriceTypeName+"=?",priceType.getPriceTypeName()).executeSingle();

           if (priceType1!=null ) return  new OperationResult("این دسته بندی وجود دارد !",false,null);

            priceType.save();
            return new OperationResult<>(null, true, null);
        } catch (Exception e) {
            return new OperationResult<>("خطا در حذف فاکتور !", false, e.getMessage());
        }

    }

    public OperationResult DeletePriceType(Info info) {

        try {

            return new OperationResult<>(null, true, null);
        } catch (Exception e) {
            return new OperationResult<>("خطا در حذف فاکتور !", false, e.getMessage());
        }

    }



}
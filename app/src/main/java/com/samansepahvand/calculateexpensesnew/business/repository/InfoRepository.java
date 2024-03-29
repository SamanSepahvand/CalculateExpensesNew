package com.samansepahvand.calculateexpensesnew.business.repository;

import android.content.Context;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.samansepahvand.calculateexpensesnew.business.metamodel.DetailMainInfo;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.db.Info;
import com.samansepahvand.calculateexpensesnew.infrastructure.Utility;


import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class InfoRepository {
    private static final String TAG = "InfoRepository";
    private static InfoRepository infoRepository = null;
    private Context context;

    private InfoRepository() {

    }


    public static InfoRepository getInstance() {
        if (infoRepository == null) {
            synchronized (InfoRepository.class) {
                if (infoRepository == null) {
                    infoRepository = new InfoRepository();
                }
            }
        }
        return infoRepository;
    }


    public OperationResult<String> AddPrice(Info info, long  id) {

        try {
            if (id!=0) {
                Info infoUpdate = new Select().from(Info.class).where("Id=?", id).executeSingle();
                if (infoUpdate != null) {
                    infoUpdate.setPrice(info.getPrice());
                    infoUpdate.setDate(info.getDate());
                    infoUpdate.setTitle(info.getTitle());

                    infoUpdate.setEnglishDate(String.valueOf(Utility.getMiladyDate()));
                    infoUpdate.setFarsiDate(Utility.getIranianDate());

                    infoUpdate.save();
                    return new OperationResult<>(null, true, null);
                }else{
                    return new OperationResult<>("خطا ", false,null);
                }
            } else {
                Date date = new Date();
                info.setDate(DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT).format(date));
                info.setEnglishDate(String.valueOf(Utility.getMiladyDate()));
                info.setFarsiDate(Utility.getIranianDate());

                info.save();
                return new OperationResult<>(null, true, null);
            }

        } catch (Exception e) {
            return new OperationResult<>("خطا در  ذخیره سازی فاکتور انتخابی", false, e.getMessage());
        }

    }




    public OperationResult DeleteItem(Info info) {

        try {
            new Delete().from(Info.class).where("Id=?", info.getId()).execute();

            return new OperationResult<>(null, true, null);
        } catch (Exception e) {
            return new OperationResult<>("خطا در حذف فاکتور !", false, e.getMessage());
        }

    }

    public OperationResult<Info> GetInfo() {
        try {
            int totalPrice = 0;
            List<Info> infos = new Select().from(Info.class)
                    .orderBy(Info.DATE+" desc")
                    .execute();
            if (infos.size() > 0) {
                for (Info info : infos) {
                    totalPrice += info.getPrice();
                    info.setFarsiDate(Utility.ShowTimeFarsi(info));

                    info.setEstimateDate(Utility.SeparateTimeForEstimate(info.getEnglishDate()).getEstimatedTime()+" روز پیش ");



                }
                return new OperationResult<>(String.valueOf(totalPrice), true, null, null, infos);
            }
            return new OperationResult<>("فاکتوری برای نمایش وجود ندارد", false, null);
        } catch (
                Exception e) {
            return new OperationResult<>("خطا در  نمایش فااکتور ها رخ داده !", false, e.getMessage());


        }


    }


    public OperationResult<DetailMainInfo> DetailMainInfo(){

        try{
            DetailMainInfo detailMainInfo=new DetailMainInfo();



            ///total Price
            int totalPrice = 0;
            List<Info> infos = new Select().from(Info.class).execute();
            if (infos.size() > 0) {
                for (Info info : infos) {
                    totalPrice += info.getPrice();
                }
            }
            detailMainInfo.setTotalPrice(totalPrice+"");
            /// invoiceCount

            detailMainInfo.setInvoiceCount(infos.size()+"");
            //getMaxPrice
            List<Info> maxPrice = new Select().from(Info.class).orderBy("price desc").execute();
            detailMainInfo.setMaxInvoicePrice(maxPrice.get(0).getPrice()+"");

            //laseDate
            detailMainInfo.setLastInvoiceDate(Utility.SeparateTimeForEstimate(infos.get(infos.size()-1).getEnglishDate()).getEstimatedTime()+" روز پیش ");

            //laseDate
            detailMainInfo.setCurrentDate(" "+Utility.ShowTimeFarsi(infos.get(infos.size()-1)));

            return new OperationResult<DetailMainInfo>(null,true,null,detailMainInfo,null);
        }catch (Exception e){
            return new OperationResult<DetailMainInfo>(null,false,e.getMessage());

        }
    }

}
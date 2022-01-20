package com.samansepahvand.calculateexpensesnew.business.repository;

import android.content.Context;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.util.SQLiteUtils;
import com.samansepahvand.calculateexpensesnew.business.metamodel.DetailMainInfo;
import com.samansepahvand.calculateexpensesnew.business.metamodel.InfoMetaModel;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.db.Info;
import com.samansepahvand.calculateexpensesnew.infrastructure.Utility;


import java.text.DateFormat;
import java.util.Collections;
import java.util.Comparator;
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

    public OperationResult<InfoMetaModel> GetInfo() {
        try {
            int totalPrice = 0;

//            List<Info> infos = new Select().from(Info.class)
//                    .orderBy("id desc")
//                    .execute();



            String query="select * from info  i " +
                    " left join priceType t on t.PriceTypeItemId=i.priceTypeIdItem and t.PriceTypeId=i.PriceTypeId  " +
                    " order by id desc";


            List<InfoMetaModel> metaModels= SQLiteUtils.rawQuery(InfoMetaModel.class,query,null);



            if (metaModels.size() > 0) {
                for (InfoMetaModel info : metaModels) {
                    totalPrice += info.getPrice();
                    info.setFarsiDate(Utility.ShowTimeFarsiMeta(info));
                    info.setEstimateDate(Utility.SeparateTimeForEstimate(info.getEnglishDate()).getEstimatedTime()+" روز پیش ");


                }


                return new OperationResult<>(String.valueOf(totalPrice), true, null, null, Utility.OrderByDateDescMeta(metaModels));
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

    public OperationResult<Info> GetInfoByMeta(InfoMetaModel infoMetaModel,String typeAction) {

        try {
            Info info=   new Select().from(Info.class).where("Id=?", infoMetaModel.getId()).executeSingle();
            if (typeAction.equals("Show")){
                return new OperationResult<>(null, true, null,info,null);
            }else if (typeAction.equals("Delete")){
                DeleteItem(info);
                return new OperationResult<>(null, true, null,info,null);
            }else{
                return new OperationResult<>("خطا در  بازیابی  !", false,null);
            }

        } catch (Exception e) {
            return new OperationResult<>("خطا در  بازیابی فاکتور !", false, e.getMessage());
        }

    }




}
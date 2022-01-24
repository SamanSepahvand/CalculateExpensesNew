package com.samansepahvand.calculateexpensesnew.business.repository;

import android.content.Context;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.util.SQLiteUtils;
import com.samansepahvand.calculateexpensesnew.business.metamodel.DetailMainInfo;
import com.samansepahvand.calculateexpensesnew.business.metamodel.InfoMetaModel;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.business.metamodel.ResultMessage;
import com.samansepahvand.calculateexpensesnew.db.Info;
import com.samansepahvand.calculateexpensesnew.infrastructure.Utility;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


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


    public OperationResult<String> AddPrice(Info info, long id) {

        try {
            if (id != 0) {
                Info infoUpdate = new Select().from(Info.class).where("Id=?", id).executeSingle();
                if (infoUpdate != null) {

                    infoUpdate.setPrice(info.getPrice());
                    infoUpdate.setDate(info.getDate());
                    infoUpdate.setTitle(info.getTitle());

                    infoUpdate.setEnglishDate(String.valueOf(Utility.getMiladyDate()));
                    infoUpdate.setFarsiDate(Utility.getIranianDate());

                    infoUpdate.save();
                    return new OperationResult<>(null, true, null);
                } else {
                    return new OperationResult<>("خطا ", false, null);
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


            String query = "select * from info  i " +
                    " left join priceType t on t.PriceTypeItemId=i.priceTypeIdItem and t.PriceTypeId=i.PriceTypeId  " +
                    " order by id desc";


            List<InfoMetaModel> metaModels = SQLiteUtils.rawQuery(InfoMetaModel.class, query, null);


            if (metaModels.size() > 0) {
                for (InfoMetaModel info : metaModels) {
                    totalPrice += info.getPrice();
                    info.setFarsiDate(Utility.ShowTimeFarsiMeta(info));
                    info.setEstimateDate(Utility.SeparateTimeForEstimate(info.getEnglishDate()).getEstimatedTime() + " روز پیش ");


                }


                return new OperationResult<>(String.valueOf(totalPrice), true, null, null, Utility.OrderByDateDescMeta(metaModels));
            }
            return new OperationResult<>("فاکتوری برای نمایش وجود ندارد", false, null);
        } catch (
                Exception e) {
            return new OperationResult<>("خطا در  نمایش فااکتور ها رخ داده !", false, e.getMessage());


        }


    }


    public OperationResult<DetailMainInfo> DetailMainInfo() {

        try {
            DetailMainInfo detailMainInfo = new DetailMainInfo();


            ///total Price
            int totalPrice = 0;
            List<Info> infos = new Select().from(Info.class).execute();
            if (infos.size() > 0) {
                for (Info info : infos) {
                    totalPrice += info.getPrice();
                }
            }
            detailMainInfo.setTotalPrice(totalPrice + "");
            /// invoiceCount

            detailMainInfo.setInvoiceCount(infos.size() + "");
            //getMaxPrice
            List<Info> maxPrice = new Select().from(Info.class).orderBy("price desc").execute();
            detailMainInfo.setMaxInvoicePrice(maxPrice.get(0).getPrice() + "");

            //laseDate
            detailMainInfo.setLastInvoiceDate(Utility.SeparateTimeForEstimate(infos.get(infos.size() - 1).getEnglishDate()).getEstimatedTime() + " روز پیش ");

            //laseDate
            detailMainInfo.setCurrentDate(" " + Utility.ShowTimeFarsi(infos.get(infos.size() - 1)));

            return new OperationResult<DetailMainInfo>(null, true, null, detailMainInfo, null);
        } catch (Exception e) {
            return new OperationResult<DetailMainInfo>(null, false, e.getMessage());

        }
    }

    public OperationResult<Info> GetInfoByMeta(InfoMetaModel infoMetaModel, String typeAction) {

        try {
            Info info = new Select().from(Info.class).where("Id=?", infoMetaModel.getId()).executeSingle();
            if (typeAction.equals("Show")) {
                return new OperationResult<>(null, true, null, info, null);
            } else if (typeAction.equals("Delete")) {
                DeleteItem(info);
                return new OperationResult<>(null, true, null, info, null);
            } else {
                return new OperationResult<>("خطا در  بازیابی  !", false, null);
            }

        } catch (Exception e) {
            return new OperationResult<>("خطا در  بازیابی فاکتور !", false, e.getMessage());
        }

    }


    public OperationResult<InfoMetaModel> GetSameInvoices(InfoMetaModel infoMetaModel) {

        try {
            String query = "select distinct *   from info  i " +
                    " left join priceType t  on  t.PriceTypeId=i.PriceTypeId  "
                    +"where i.PriceTypeId= "+infoMetaModel.getPriceTypeId()+"  and  i.id<>"+infoMetaModel.getId()+
                    " order by id asc  limit 10";
            List<InfoMetaModel> metaModels = SQLiteUtils.rawQuery(InfoMetaModel.class, query, null);






            if (metaModels==null)return new OperationResult<>(ResultMessage.ErrorNewMessage, false, null);
            List<InfoMetaModel> newResult= removeDuplicates(metaModels);


            InfoMetaModel first=new InfoMetaModel();
            first.setPriceTypeId(newResult.get(0).getPriceTypeId());

           first.setTitle("مشاهده همه");


            InfoMetaModel last=new InfoMetaModel();
            last.setPriceTypeId(newResult.get(0).getPriceTypeId());
            last.setTitle("مشاهده همه >");



            newResult.add(0,last);
            newResult.add(newResult.size(),first);

            return new OperationResult<>(null ,true, null,null,newResult);


        } catch (Exception e) {
            return new OperationResult<>(ResultMessage.ErrorNewMessage, false, null);

        }
    }

    public List<InfoMetaModel> removeDuplicates(List<InfoMetaModel> list) {
        // Set set1 = new LinkedHashSet(list);
        Set set = new TreeSet(new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                if (((InfoMetaModel) o1).getId().toString().equalsIgnoreCase(((InfoMetaModel) o2).getId().toString()) /*&&
                    ((Blog)o1).getName().equalsIgnoreCase(((Blog)o2).getName())*/) {
                    return 0;
                }
                return 1;
            }
        });
        set.addAll(list);

        final List newList = new ArrayList(set);
        return newList;


    }

}
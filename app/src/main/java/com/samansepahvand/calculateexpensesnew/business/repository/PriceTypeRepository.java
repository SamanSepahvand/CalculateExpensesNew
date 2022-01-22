package com.samansepahvand.calculateexpensesnew.business.repository;

import static com.samansepahvand.calculateexpensesnew.business.domain.Constants.BuyPriceTypeItem;

import android.content.Context;
import android.util.SparseArray;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;
import com.samansepahvand.calculateexpensesnew.business.metamodel.DetailMainInfo;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.business.metamodel.ResultMessage;
import com.samansepahvand.calculateexpensesnew.business.metamodel.UserInformations;
import com.samansepahvand.calculateexpensesnew.db.Info;
import com.samansepahvand.calculateexpensesnew.db.PriceType;
import com.samansepahvand.calculateexpensesnew.infrastructure.Utility;
import com.samansepahvand.calculateexpensesnew.infrastructure.expandableListView.PriceTypeHeader;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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


    public OperationResult AddDefaultPriceType() {
        try {
            PriceType priceType = new Select().from(PriceType.class).executeSingle();
            if (priceType != null) return new OperationResult<>(null, true, null);
            for (int i = 0; i < Constants.PriceTypeHeader.length; i++) {
                switch (i) {
                    case 0:
                        fillPriceType(Constants.BuyPriceTypeItem, i);
                        break;
                    case 1:
                        fillPriceType(Constants.RentPriceTypeItem, i);
                        break;
                    case 2:
                        fillPriceType(Constants.CarPriceTypeItem, i);
                        break;
                    case 3:
                        fillPriceType(Constants.FoodPriceTypeItem, i);
                        break;
                    case 4:
                        fillPriceType(Constants.CommutingPriceTypeItem, i);
                        break;
                    case 5:
                        fillPriceType(Constants.EntertainmentPriceTypeItem, i);
                        break;
                    case 6:
                        fillPriceType(Constants.BillsPriceTypeItem, i);
                        break;

                }
            }
            return new OperationResult<>(null, true, null);
        } catch (Exception e) {
            return new OperationResult<>("خطا در ثبت دسته بندی  !", false, e.getMessage());
        }

    }

    private void fillPriceType(String[] array, int headerId) {

        for (int j = 0; j < array.length; j++) {

            Calendar cal = Calendar.getInstance();
            PriceType priceType = new PriceType();
            priceType.setPriceTypeId(headerId + "");
            priceType.setPriceTypeName(Constants.PriceTypeHeader[headerId]);
            priceType.setPriceTypeItemName(array[j]);
            priceType.setPriceTypeItemId(j);
            priceType.setPriceCreatorUserId(0);
            priceType.setPriceCreationDate(cal.getTime() + "");
            priceType.save();

        }

    }


    public OperationResult AddPriceType(PriceType priceType) {
        try {

            PriceType priceType1 = new Select().from(PriceType.class).where(PriceType.PriceTypeName + "=?", priceType.getPriceTypeName()).executeSingle();

            if (priceType1 != null)
                return new OperationResult("این دسته بندی وجود دارد !", false, null);

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


    public OperationResult<SparseArray<PriceTypeHeader>> GetPriceTypeCategory(int userId) {
        try {
            SparseArray<PriceTypeHeader> priceTypeHeaderList = new SparseArray<PriceTypeHeader>();
            List<PriceType> list = new Select().from(PriceType.class)
                    .where("PriceCreatorUserId=? or PriceCreatorUserId=?", userId, 0)
                    .execute();
            if (list == null) return new OperationResult(ResultMessage.ErrorMessage, false, null);


            for (int j = 0; j < Constants.PriceTypeHeader.length; j++) {
                PriceTypeHeader group = new PriceTypeHeader(j, Constants.PriceTypeHeader[j],Constants.PriceTypeHeaderPicture[j]);
                for (PriceType item : list){
                    if (item.getPriceTypeId().equals(j+"")){
                        group.priceTypeList.add(item);
                        priceTypeHeaderList.append(j, group);
                    }

                }

            }

            return new OperationResult(ResultMessage.ErrorMessage, true, null, priceTypeHeaderList, null);

        } catch (Exception e) {

            return new OperationResult(ResultMessage.ErrorMessage, false, null);
        }

    }

    public OperationResult<PriceType> GetLastIdPriceType(String tempPriceTypeIdHeader) {

        try {

            PriceType priceType = new Select().from(PriceType.class)
                    .where(PriceType.PriceTypeId + "=?", tempPriceTypeIdHeader)
                    .orderBy("id desc")
                    .executeSingle();

            if (priceType == null)
                return new OperationResult("این دسته بندی وجود دارد !", false, null);

            return new OperationResult<>(null, true, null,priceType,null);

        } catch (Exception e) {

            return new OperationResult<>("خطا در دریافت آخرین شماره دسته بندی !", false, e.getMessage());
        }

    }

    public OperationResult<PriceType> AddUserPriceType(PriceType priceType) {
        try {

            if (priceType == null)
                return new OperationResult("این دسته بندی  خالی می باشد !", false, null);

            priceType.save();
            return new OperationResult<>(null, true, null);

        } catch (Exception e) {

            return new OperationResult<>("خطا در ذخیره دسته بندی شما !", false, e.getMessage());
        }

    }
}
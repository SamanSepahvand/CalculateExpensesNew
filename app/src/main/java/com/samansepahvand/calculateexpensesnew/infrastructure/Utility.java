package com.samansepahvand.calculateexpensesnew.infrastructure;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.internal.$Gson$Types;
import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;
import com.samansepahvand.calculateexpensesnew.business.metamodel.CalculateDate;
import com.samansepahvand.calculateexpensesnew.business.metamodel.DateModel;
import com.samansepahvand.calculateexpensesnew.business.metamodel.DateSingle;
import com.samansepahvand.calculateexpensesnew.business.metamodel.InfoMetaModel;
import com.samansepahvand.calculateexpensesnew.business.metamodel.TimeMetaModel;
import com.samansepahvand.calculateexpensesnew.db.Info;
import com.samansepahvand.calculateexpensesnew.helper.DateConverter;
import com.samansepahvand.calculateexpensesnew.ui.modal.AlertDialogModal;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.security.auth.login.LoginException;

public class Utility {

    private static final String TAG = "Utility";


    public static String splitDigits(int number) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat();
            DecimalFormatSymbols decimalFormatSymbol = new DecimalFormatSymbols();
            decimalFormatSymbol.setGroupingSeparator(',');
            decimalFormat.setDecimalFormatSymbols(decimalFormatSymbol);
            return decimalFormat.format(number);
        } catch (Exception ex) {
            return String.valueOf(number);
        }
    }
    public static   String  GetEngDate() {
        Date date1 = new Date();
          return   DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT).format(date1);
    }


    public static Date getMiladyDate() {
        Date date = new Date();
        return date;
    }


    public static String getDayName(String date) {
        try {


            Date date1 = new Date(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            Calendar c = Calendar.getInstance();

            for (int i = 0; i < 7; i++) {
                c.add(Calendar.DATE, 1);
            }
            DateConverter converter = new DateConverter();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            String farsiDay = converter.FarsiDay(day);
            return farsiDay;
        } catch (Exception e) {
            return "";
        }
    }

    public static String ShowTimeFarsi(Info config) {
        //GetFarsiDate
        String[] farsiDate = config.getFarsiDate().split(" ");
        //Remove sec in Time
        String[] time = config.getFarsiDate().split(" ")[1].split(":");
        Spanned strHtml = Html.fromHtml((config.getEnglishDate() != null ? " <font color=#2e9699> <b> " + Utility.getDayName(config.getEnglishDate()) + farsiDate[0] + " ساعت:  " + time[0] + ":" + time[1] + " </b></font>" : "زمان نامشخص می باشد ! "));
        return strHtml.toString();


    }

    public static String ShowTimeFarsiMeta(InfoMetaModel config) {
        //GetFarsiDate
        String[] farsiDate = config.getFarsiDate().split(" ");
        //Remove sec in Time
        String[] time = config.getFarsiDate().split(" ")[1].split(":");
        Spanned strHtml = Html.fromHtml((config.getEnglishDate() != null ? " <font color=#2e9699> <b> " + Utility.getDayName(config.getEnglishDate()) + farsiDate[0] + " ساعت:  " + time[0] + ":" + time[1] + " </b></font>" : "زمان نامشخص می باشد ! "));
        return strHtml.toString();


    }

    public static List<InfoMetaModel>
    OrderByDateDescMeta(List<InfoMetaModel> myList) {
        Collections.sort(myList, new Comparator<InfoMetaModel>() {
            public int compare(InfoMetaModel obj1, InfoMetaModel obj2) {
                // ## Ascending order
//                return obj1.getProductCode().compareToIgnoreCase(obj2.getProductCode); // To compare string values
                //  return Integer.valueOf(obj1.getId()+"").compareTo(Integer.valueOf(obj2.getId()+"")); // To compare integer values
                // ## Descending order
                return obj2.getId().intValue() + "".compareToIgnoreCase(obj1.getId().intValue() + ""); // To compare string values
                // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
            }
        });

        return myList;
    }


    public static List<Info>
    OrderByDateDesc(List<Info> myList) {
        Collections.sort(myList, new Comparator<Info>() {
            public int compare(Info obj1, Info obj2) {
                // ## Ascending order
//                return obj1.getProductCode().compareToIgnoreCase(obj2.getProductCode); // To compare string values
                //  return Integer.valueOf(obj1.getId()+"").compareTo(Integer.valueOf(obj2.getId()+"")); // To compare integer values
                // ## Descending order
                return obj2.getId().intValue() + "".compareToIgnoreCase(obj1.getId().intValue() + ""); // To compare string values
                // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
            }
        });

        return myList;
    }


//    public static String getIranianDate() {
//        com.cinnadco.warehouse.helper.converter.CalendarTool tool = new com.cinnadco.warehouse.helper.converter.CalendarTool();
//        Calendar cal = Calendar.getInstance();
//        String date = String.format(Locale.US, "%d/%02d/%02d %02d:%02d:%02d",
//                tool.getIranianYear(), tool.getIranianMonth(),
//                tool.getIranianDay(), cal.get(Calendar.HOUR_OF_DAY),
//                cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
//        return date;
//    }


    public static String getIranianDate() {
        com.cinnadco.warehouse.helper.converter.CalendarTool tool = new com.cinnadco.warehouse.helper.converter.CalendarTool();
        Calendar cal = Calendar.getInstance();
        String date = String.format(Locale.US, "%d/%02d/%02d %02d:%02d:%02d",
                tool.getIranianYear(), tool.getIranianMonth(),
                tool.getIranianDay(), cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
        return date;
    }

    public static String getIranianDateInt() {
        com.cinnadco.warehouse.helper.converter.CalendarTool tool = new com.cinnadco.warehouse.helper.converter.CalendarTool();
        String date = String.format(Locale.US, "%d/%02d/%02d",
                tool.getIranianYear(), tool.getIranianMonth(),
                tool.getIranianDay());
        return date;
    }


    public static DateSingle getIranianDateCustomNew(Calendar cal1) {
        com.cinnadco.warehouse.helper.converter.CalendarTool tool = new com.cinnadco.warehouse.helper.converter.CalendarTool(cal1);
        DateSingle dd = new DateSingle();
        dd.setFarsiDate(tool.getIranianFormattedDate());
        dd.setEngDate(tool.getGregorianDate());
        return dd;
    }


    public static DateModel GetFirstLastDayMonthFarsi() {

        DateModel result = new DateModel();


        List<CalculateDate> dateListFinall = new ArrayList<>();
        dateListFinall.addAll(getCalculateDate(true));
        dateListFinall.addAll(getCalculateDate(false));

        List<CalculateDate> newDate = new ArrayList<>();

        for (CalculateDate item : dateListFinall) {
            CalculateDate calculateDate = new CalculateDate();
            String[] data = item.getFarsiDate().split("/");
            if (data[1].equals("11")) {
                calculateDate.setActionDate(item.getActionDate());
                calculateDate.setFarsiDate(item.getFarsiDate());
                calculateDate.setEngDate(item.getEngDate());

                newDate.add(calculateDate);
            }
        }


        result.setFromDate(newDate.get(0).getActionDate());
        result.setFromDateFarsi(newDate.get(0).getFarsiDate());

        result.setToDate(newDate.get(newDate.size() - 1).getActionDate());
        result.setToDateFarsi(newDate.get(newDate.size() - 1).getFarsiDate());

        return result;


    }

    private static List<CalculateDate> getCalculateDate(boolean isPrev) {

        List<CalculateDate> dateList1111 = new ArrayList<>();


        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        if (isPrev)
            cal.add(Calendar.MONTH, -1);

        int maxDay1 = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");

        DateSingle dateSingle1 = getIranianDateCustomNew(cal);

        CalculateDate calculateDate11 = new CalculateDate();
        calculateDate11.setActionDate(Integer.parseInt(df1.format(cal.getTime())));
        calculateDate11.setFarsiDate(dateSingle1.getFarsiDate());
        calculateDate11.setEngDate(dateSingle1.getEngDate());
        dateList1111.add(calculateDate11);
        for (int i = 1; i < maxDay1; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i + 1);
            DateSingle dateSingle11 = getIranianDateCustomNew(cal);
            CalculateDate calculateDate121 = new CalculateDate();
            calculateDate121.setActionDate(Integer.parseInt(df1.format(cal.getTime())));
            calculateDate121.setFarsiDate(dateSingle11.getFarsiDate());
            calculateDate121.setEngDate(dateSingle11.getEngDate());
            dateList1111.add(calculateDate121);
        }

        return dateList1111;

    }


    public static String getIranianDateCustom(Calendar cal1) {
        com.cinnadco.warehouse.helper.converter.CalendarTool tool = new com.cinnadco.warehouse.helper.converter.CalendarTool(cal1);
        String date = String.format(Locale.US, "%d/%02d/%02d %02d:%02d:%02d",
                tool.getIranianYear(), tool.getIranianMonth(),
                tool.getIranianDay(), cal1.get(Calendar.HOUR_OF_DAY),
                cal1.get(Calendar.MINUTE), cal1.get(Calendar.SECOND));
        return date;
    }


    public static TimeMetaModel SeparateTimeForEstimate(String fullDate) {

        TimeMetaModel timeModel = new TimeMetaModel();

        String time = null;
        try {
            String strDate = fullDate;
            //calculate time

            String[] fulltime = strDate.split(" ");
            String[] removeSec = fulltime[1].split(":");

            //calculate engdate
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date date = sdf.parse(fullDate.toString());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);


            //calculate farsi date
//            String[] array1 = strDate.split(" ");
//            String[] array = array1[0].split("-");
//            int day = Integer.parseInt(array[2]);
//            int month = Integer.parseInt(array[1]);
//            int year = Integer.parseInt(array[0]);
//            DateConverter dateConverter = new DateConverter();
//            dateConverter.gregorianToPersian(year, month, day);

            //calculate  estimate


            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date date1 = sdf1.parse(strDate);
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            int dayRemain = getDaysDifference(cal1.getTime(), calendar.getTime());


            //set value to model
//            timeModel.setEngDate(cal.getTime() + "");
//            timeModel.setTime(removeSec[0] + ":" + removeSec[1]);
//            timeModel.setFarsiDate(dateConverter.getYear() + "/" + dateConverter.getMonth() + "/" + dateConverter.getDay());
//
            timeModel.setEstimatedTime(dayRemain);

        } catch (Exception e) {


            timeModel.setFarsiDate("");
            timeModel.setTime("");
            timeModel.setEngDate("");
            timeModel.setEstimatedTime(1);

        }
        return timeModel;


    }


    public static int getDaysDifference(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null)
            return 0;

        return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }


    public static int ChangeIconAddExpenses(int count) {
        if (count == 1) return R.drawable.ic_baseline_check_24;
        return R.drawable.ic_baseline_arrow_back_ios_24;

    }

    public static int GetPrice(String priceString) {

        try {
            String split = priceString.replace(",", "");
            return Integer.parseInt(split);
        } catch (Exception e) {
            return 0;
        }
    }


    public static SpannableString TextWithUnderLineStyle(String FarsiDate) {
        SpannableString content = new SpannableString(FarsiDate);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;
    }


    public static void DialogSuccess(String info, Context context) {
        final AlertDialogModal modal2 = new AlertDialogModal(context, true, false);
        modal2.setImageTypeCustom(Constants.TypeImageAlertDialog[3]);
        modal2.setButtonConfirmCustom("باشه !", Constants.TypeButtonStyleAlertDialog[4]);

        modal2.setTextContent(info);
        modal2.setAcceptButton(new AlertDialogModal.OnAcceptInterface() {
            @Override
            public void accept() {
                modal2.dismiss();
            }
        });
        modal2.show();

    }

    public static void DialogFailed(String info, Context context) {
        final AlertDialogModal modal2 = new AlertDialogModal(context, true, false);
        modal2.setImageTypeCustom(Constants.TypeImageAlertDialog[2]);
        modal2.setButtonConfirmCustom("باشه !", Constants.TypeButtonStyleAlertDialog[2]);

        modal2.setTextContent(info);
        modal2.setAcceptButton(new AlertDialogModal.OnAcceptInterface() {
            @Override
            public void accept() {
                modal2.dismiss();
            }
        });
        modal2.show();

    }


    public static int GetActionDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar mDate = Calendar.getInstance();
        return Integer.parseInt(dateFormat.format(mDate.getTime()));
    }


}

package com.samansepahvand.calculateexpensesnew.business.domain;

import android.graphics.Typeface;

import com.samansepahvand.calculateexpensesnew.MainApplication;
import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.metamodel.UserInformations;

public class Constants {


    public static  String  TABLET_VERSION_NAME="";

    public static final int DelayTimeSplash = 2500;
    public static final int DelayFast = 500;
    public static final int DelayMoreFast = 300;
    public static final int DelayTime = 1500;
    public static final int DelayTimeDialogAnimation = 500;


    public static Typeface CustomStyleElement() {
        Typeface fontIranSans = Typeface.createFromAsset(MainApplication.getAppMainContext().getAssets(),
                "fonts/iran_sans.ttf");

        return fontIranSans;


    }



    public static int[] TypeImageAlertDialog = new int[]{

            R.drawable.ic_baseline_info_black_24,   //0
            R.drawable.ic_baseline_info_yellow_24,  //1
            R.drawable.ic_baseline_warning_24,      //2
            R.drawable.ic_baseline_check_circle_24, //3
    };


    public static int[] TypeButtonStyleAlertDialog = new int[]{

            R.drawable.shape_background_button_dialog_cancel,  //0
            R.drawable.shape_background_button_dialog_yellow,  //1
            R.drawable.shape_background_button_dialog_red,     //2
            R.drawable.shape_background_button_dialog_confirm, //3
            R.drawable.shape_background_button_dialog_confirm_light, //4

    };
    public static String exitMessage() {
        String data = "کاربر <font color=red><b>"+ UserInformations.getFullName() +"</b></font> آیا مایل به خروج از برنامه می باشید ؟";
        return data;
    }




    public static String[] PriceTypeHeader = new String[]{

            "خرید",
            "اجاره",
            "ماشین",
            "خوراک",
            "رفت و آمد",
            "سرگرمی",
            "قبوض"

    };


    public static String[] CarPriceTypeItem = new String[]{
            "بنزین",
            "بیمه",
            "جریمه",
            "تعمیرگاه"
    };



    public static String[] BuyPriceTypeItem = new String[]{
            "لباس",
            "هدیه",
            "کارت شارژ",
            "اینترنت",
            "دارو",
            "کتاب",
            "کالا"
    };



    public static String[] RentPriceTypeItem = new String[]{
            "خانه",
            "شرکت",
            "مغازه"
    };

    public static String[] FoodPriceTypeItem = new String[]{
            "رستوران",
            "کافی شاپ",
            "آنلاین شاپینگ"
    };


    public static String[] CommutingPriceTypeItem = new String[]{
            "اتوبوس",
            "تاکسی",
            "مترو"
    };

    public static String[] EntertainmentPriceTypeItem = new String[]{
            "شهربازی",
            "سینما",
            "تئاتر",
            "کنسرت"
    };

    public static String[] BillsPriceTypeItem = new String[]{
            "موبایل",
            "تلفن",
            "برق",
            "آب"
    };



}

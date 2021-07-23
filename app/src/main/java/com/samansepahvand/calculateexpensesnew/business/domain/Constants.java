package com.samansepahvand.calculateexpensesnew.business.domain;

import android.graphics.Typeface;

import com.samansepahvand.calculateexpensesnew.MainApplication;
import com.samansepahvand.calculateexpensesnew.R;

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




}

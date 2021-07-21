package com.samansepahvand.calculateexpensesnew;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.activeandroid.ActiveAndroid;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends Application {
    private static Context context;
    private PackageInfo pinfo = null;
    private static Animation animation;
    private Context _context;

    public MainApplication() {

    }
    public MainApplication(Context context) {
        this._context=context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MainApplication.context = getApplicationContext();
        ActiveAndroid.initialize(this);
        fontAssign();
        createNotificationChannel();

        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

      Constants.TABLET_VERSION_NAME = pinfo.versionName;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 =
                    new NotificationChannel("CHANNEL_ID_1",
                            "Channel(1)", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Channel 1 Desc..");

            NotificationChannel channel2 =
                    new NotificationChannel("CHANNEL_ID_2",
                            "Channel(2)", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Channel 2 Desc..");

            //HERE ABOVE DOUBT

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
            notificationManager.createNotificationChannel(channel2);


        }
    }




    public void fontAssign() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iran_sans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .disableCustomViewInflation()
                .build());
    }

    public static Context getAppMainContext() {
        return MainApplication.context;
    }

//    public   void CopyContent(Context context, String content, String type){
//        new CustomToast(context,type+" با موفقیت کپی شد ! ").show();
//        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
//        ClipData clip = ClipData.newPlainText("label",content );
//        clipboard.setPrimaryClip(clip);
//    }
//
    public  static Animation SetAnimation(String typeAnimation) {

        switch (typeAnimation) {
            case "Rotate":
                animation = AnimationUtils.loadAnimation(context, R.anim.anim_rotate_menu);
                break;
            case "RotateSlow":
                animation = AnimationUtils.loadAnimation(context, R.anim.rotate_anim);
                break;
            case "FadeIn":
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_anim_dialog);
                break;
            case "FadeInLong":
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_anim);
                break;
            case "SlideUp":
                animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
                break;
            case "SlideUpSlow":
                animation = AnimationUtils.loadAnimation(context, R.anim.slide_up_slow);
                break;
            case "FadeOut":
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
                break;





        }
        return animation;

    }
//
//




}

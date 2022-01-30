package com.samansepahvand.calculateexpensesnew.ui.dialog;

import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.DialogFailed;
import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.DialogSuccess;
import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.getIranianDateCustom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cinnadco.warehouse.helper.converter.CalendarTool;
import com.google.android.material.textfield.TextInputLayout;
import com.samansepahvand.calculateexpensesnew.MainApplication;
import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;
import com.samansepahvand.calculateexpensesnew.business.domain.Enumerations;
import com.samansepahvand.calculateexpensesnew.business.metamodel.DateModel;
import com.samansepahvand.calculateexpensesnew.business.metamodel.DateSingle;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.business.metamodel.UserInformations;
import com.samansepahvand.calculateexpensesnew.business.repository.PriceTypeRepository;
import com.samansepahvand.calculateexpensesnew.db.PriceType;
import com.samansepahvand.calculateexpensesnew.infrastructure.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;


public class DialogChooseDate extends Dialog implements View.OnClickListener {


    private Context mContext;


    private Button btnConfirm, btnCancel;

    private ImageView imgCloseDialog;

    private OnAcceptInterface acceptListener;
    private OnCancelInterface cancelListener;


    private TextView txtFromDate, txtToDate;


    private DateModel dateModel=new DateModel();

    public DialogChooseDate(@NonNull Context context, boolean hasConfirm, boolean hasCancel) {
        super(context);
        this.mContext = context;
        setContentView(R.layout.dialog_choose_date);
        this.getWindow().getAttributes().windowAnimations = R.style.AlertDialogAnimation;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView(hasConfirm, hasCancel);
        // initValue(alertDialogModel);




    }





    private void initView(final boolean hasConfirm, final boolean hasCancel) {


        btnConfirm = findViewById(R.id.btn_dialog_confirm);
        btnCancel = findViewById(R.id.btn_dialog_cancel);
        imgCloseDialog = findViewById(R.id.img_dialog_close);
        txtFromDate = findViewById(R.id.txt_from_date);
        txtToDate = findViewById(R.id.txt_to_date);


        txtFromDate.setVisibility(View.GONE);
        txtToDate.setVisibility(View.GONE);


        btnConfirm.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                btnConfirm.startAnimation(MainApplication.SetAnimation("FadeIn"));
                // btnCancel.startAnimation(MainApplication.SetAnimation("FadeIn"));
                txtToDate.startAnimation(MainApplication.SetAnimation("FadeIn"));
                txtFromDate.startAnimation(MainApplication.SetAnimation("FadeIn"));


                if (hasCancel) btnCancel.setVisibility(View.VISIBLE);
                if (hasConfirm) btnConfirm.setVisibility(View.VISIBLE);

//                txtBody.setVisibility(View.VISIBLE);
//                imgTypeDialog.setVisibility(View.VISIBLE);

                txtFromDate.setVisibility(View.VISIBLE);
                txtToDate.setVisibility(View.VISIBLE);


            }
        }, Constants.DelayMoreFast);

        imgCloseDialog.startAnimation(MainApplication.SetAnimation("Rotate"));



        txtToDate.setText(Utility.TextWithUnderLineStyle("انتخاب کنید."));
        txtFromDate.setText(Utility.TextWithUnderLineStyle("انتخاب کنید."));




        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imgCloseDialog.setOnClickListener(this);
        txtFromDate.setOnClickListener(this);
        txtToDate.setOnClickListener(this);




    }




    public Dialog setAcceptButton(OnAcceptInterface acceptListener) {
        this.acceptListener = acceptListener;
        //btnCancel.setVisibility(View.VISIBLE);
        return this;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_dialog_close:
                animOpenCloseDialog();
                break;
            case R.id.btn_dialog_cancel:
                animOpenCloseDialog();
                if (cancelListener != null)
                    cancelListener.cancel();
                break;
            case R.id.btn_dialog_confirm:
                if (acceptListener != null){
                    if (dateModel!=null){
                        acceptListener.accept(dateModel);
                    }else{
                        Toast.makeText(getContext(), "لطفا بازه زمانی تاریخ را کامل وارد کنید.", Toast.LENGTH_SHORT).show();
                    }
                }
                dismiss();
                break;
            case R.id.txt_from_date:
                PersianDataPicker(Enumerations.DateType.FROM_DATE);
                break;
            case R.id.txt_to_date:
                PersianDataPicker(Enumerations.DateType.TO_DATE);
                break;

        }
       // dismiss();
    }


    private void PersianDataPicker(final int dateType) {

        String suggest = Utility.getIranianDateInt();
        String[] suggestDate =suggest.split("/");


        PersianDatePickerDialog picker = new PersianDatePickerDialog(getContext())
                .setPositiveButtonString("تایید")
                .setNegativeButton("بیخیال !")
                .setTodayButton("تاریخ امروز")
                .setTodayButtonVisible(true)
                .setMinYear(1300)
                .setMaxYear(1450)
                .setInitDate( Integer.parseInt(suggestDate[0]),Integer.parseInt(suggestDate[1]),Integer.parseInt(suggestDate[2]))
                .setActionTextColor(Color.GRAY)
                //    .setTypeFace(Constants.CustomStyleElement())
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(new PersianPickerListener() {
                    @Override
                    public void onDateSelected(PersianPickerDate persianPickerDate) {
//                        Log.e(TAG, "onDateSelected: " + persianPickerDate.getTimestamp());//675930448000
//                        Log.e(TAG, "onDateSelected: " + persianPickerDate.getGregorianDate());//Mon Jun 03 10:57:28 GMT+04:30 1991
//                        Log.e(TAG, "onDateSelected: " + persianPickerDate.getPersianLongDate());// دوشنبه  13  خرداد  1370
//                        Log.e(TAG, "onDateSelected: " + persianPickerDate.getPersianMonthName());//خرداد
//                        Log.e(TAG, "onDateSelected: " + persianPickerDate.getGregorianDay());//خرداد
//                        Log.e(TAG, "onDateSelected: " + PersianCalendarUtils.isPersianLeapYear(persianPickerDate.getPersianYear()));//true
//                        Log.e(TAG, "onDateSelected: " + persianPickerDate.getGregorianDay());//خرداد

                        Date selectedDate = persianPickerDate.getGregorianDate();
                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");


                        Calendar calendar=Calendar.getInstance();
                        calendar.setTime(selectedDate);


                   DateSingle dateSingle= Utility.getIranianDateCustomNew(calendar);




                        switch (dateType){
                            case Enumerations.DateType.FROM_DATE:
                                txtFromDate.setText(Utility.TextWithUnderLineStyle(persianPickerDate.getPersianLongDate()));
                                dateModel.setFromDate(Integer.parseInt(dateFormat.format(selectedDate)));
                                dateModel.setFromDateFarsi(dateSingle.getFarsiDate());


                                break;
                            case Enumerations.DateType.TO_DATE:
                                txtToDate.setText(Utility.TextWithUnderLineStyle(persianPickerDate.getPersianLongDate()));
                                dateModel.setToDate(Integer.parseInt(dateFormat.format(selectedDate)));
                                dateModel.setToDateFarsi(dateSingle.getFarsiDate());

                                break;


                        }

                    }

                    @Override
                    public void onDismissed() {

                    }
                });
        picker.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void animOpenCloseDialog() {
        imgCloseDialog.startAnimation(MainApplication.SetAnimation("Rotate"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, Constants.DelayTimeDialogAnimation);
    }





    public interface OnAcceptInterface {
        void accept(DateModel dateModel);
    }

    public interface OnCancelInterface {
        void cancel();
    }
}

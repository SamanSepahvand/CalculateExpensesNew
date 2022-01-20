package com.samansepahvand.calculateexpensesnew.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.business.metamodel.ResultMessage;
import com.samansepahvand.calculateexpensesnew.business.metamodel.UserInformations;
import com.samansepahvand.calculateexpensesnew.business.repository.InfoRepository;
import com.samansepahvand.calculateexpensesnew.db.Info;
import com.samansepahvand.calculateexpensesnew.db.PriceType;
import com.samansepahvand.calculateexpensesnew.infrastructure.Utility;
import com.samansepahvand.calculateexpensesnew.ui.adapter.MyExpandableListAdapter;
import com.samansepahvand.calculateexpensesnew.ui.dialog.DialogFragmentPriceType;
import com.samansepahvand.calculateexpensesnew.ui.dialog.DialogFragmentPriceTypeNew;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;

import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.ChangeIconAddExpenses;
import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.DialogFailed;
import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.DialogSuccess;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddExpensesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExpensesFragment extends Fragment implements View.OnClickListener, DialogFragmentPriceType.IPriceTypeNew, View.OnLongClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    //component
    private EditText edtTitle, edtPrice;
    private TextView txtDate, txtCurrentMoney;

    private ImageView imgInsertBack;
    private String persianTime;
    private boolean keyUpdate = false;
    private Info infoData;
    private Long id;

    private StringBuilder sepPrice = new StringBuilder();

    private NavController navController;

    private String titleLength, priceLength;

    private String current = "";


    private TextView txtDateChoose;
    private EditText txtInvoiceShow;
    private TextView txtPriceType;


    private String date, time, invoiceTitle, invoicePrice;

    private static final String TAG = "AddExpensesFragment";


    private SharedPreferences preferences;

    private TextView txtPriceTypeResult;

    private  PriceType priceType =new PriceType();

    public AddExpensesFragment() {
        // Required empty public constructor
    }


    public static AddExpensesFragment newInstance(String param1, String param2) {
        AddExpensesFragment fragment = new AddExpensesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_expenses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiView(view);
        preferences = getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);

    }

    private void intiView(View view) {
        navController = Navigation.findNavController(view);
        id = (long) 0;
        txtCurrentMoney = view.findViewById(R.id.txt_curent_money);
        imgInsertBack = view.findViewById(R.id.img_back);
        edtPrice = view.findViewById(R.id.edt_price);
        txtDateChoose = view.findViewById(R.id.txt_date_chosse);
        txtInvoiceShow = view.findViewById(R.id.txt_invoice_show);
        txtPriceType = view.findViewById(R.id.txt_price_type);

        txtPriceTypeResult = view.findViewById(R.id.txt_price_type_result);


        edtTitle = view.findViewById(R.id.edt_name);

        Bundle bundle = getArguments();
        if (bundle != null) {
            infoData = (Info) bundle.getSerializable("Info");
            if (infoData != null) {

                StateLive(infoData.getTitle(), infoData.getPrice() + "");

                edtPrice.setText(infoData.getPrice() + "");
                edtTitle.setText(infoData.getTitle());
                id = infoData.getId();
                //  id = bundle.getLong("Id", 0);
                keyUpdate = true;

            }


        }
        EdtOnline();

        //Onclick Response in Android we need this problem, to silver;
        imgInsertBack.setOnClickListener(this);
        txtDateChoose.setOnClickListener(this);
        txtPriceType.setOnClickListener(this);

        txtPriceTypeResult.setOnLongClickListener(this);
        PersianDataPicker();


    }


    private void EdtOnline() {


        txtInvoiceShow.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //   OnlineInvoices();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        edtPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                OnlineInvoices();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                titleLength = s.toString();
                StateLive(titleLength, priceLength);

                try {
                    if (!s.toString().equals(current)) {
                        edtPrice.removeTextChangedListener(this);
                        String cleanString = s.toString().replaceAll("[$,.]", "");
                        double parsed = Double.parseDouble(cleanString.equals("") ? "0" : cleanString);
                        String formatted = NumberFormat.getNumberInstance().format(parsed);
                        current = formatted;
                        edtPrice.setText(formatted);
                        edtPrice.setSelection(formatted.length());
                        edtPrice.addTextChangedListener(this);

                        invoicePrice = formatted;
                    }
                } catch (Exception e) {

                }

            }
        });


        edtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                priceLength = s.toString();
                StateLive(titleLength, priceLength);

                invoiceTitle = s.toString();

            }


        });
    }

    private void StateLive(String priceLength, String titleLength) {
        if (priceLength == null || priceLength.length() == 0 || titleLength == null || titleLength.length() == 0)
            imgInsertBack.setImageResource(ChangeIconAddExpenses(0));
        else imgInsertBack.setImageResource(ChangeIconAddExpenses(1));


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                String title = edtTitle.getText().toString();
                String price = edtPrice.getText().toString();


                if (validate(title, price)) {
                    AddNewPrice();
                } else {
                    Toast.makeText(getContext(), "Null Error", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.txt_date_chosse:
                PersianDataPicker();
                break;

            case R.id.txt_price_type:
                OpenPriceTypeDialog();

                break;

        }

    }

    private boolean validate(String title, String price) {
        if ((title == null || title.equals("") || price.equals("") || price == null))
            return false;
        return true;
    }

    private void PersianDataPicker() {


        PersianDatePickerDialog picker = new PersianDatePickerDialog(getContext())
                .setPositiveButtonString("تایید")
                .setNegativeButton("بیخیال !")
                .setTodayButton("تاریخ امروز")
                .setTodayButtonVisible(true)
                .setMinYear(1300)
                .setMaxYear(1450)
                .setInitDate(1400, 3, 1)
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

                        Date test = persianPickerDate.getGregorianDate();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                        String strDate = dateFormat.format(test);

                        txtDateChoose.setText(persianPickerDate.getPersianLongDate());
                        date = persianPickerDate.getPersianLongDate();
                        OnlineInvoices();


//                        persianTime = persianPickerDate.getGregorianDate() + "";
//                        String date = persianPickerDate.getGregorianYear() + "" + persianPickerDate.getGregorianMonth() + "" + persianPickerDate.getGregorianDay();

                        //  persianTime = persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay();

                    }

                    @Override
                    public void onDismissed() {

                    }
                });
        picker.show();
    }


    private void OnlineInvoices() {

        Spanned strHtml = Html.fromHtml(" شما در تاریخ  " + "<font color='red'>"
                + date + "</font>" + " مبلغ  " + "<font color='red'>" + invoicePrice
                + "</font>" + " بابت هزینه  " + "<font color='red'>" + invoiceTitle + "</font>" + " پرداخت کرده اید. ");

        txtInvoiceShow.setText(strHtml);

    }


    private void AddNewPrice() {

        Info info = new Info();
        info.setTitle(edtTitle.getText().toString());
        info.setPrice(Utility.GetPrice(edtPrice.getText().toString()));
        info.setDate(keyUpdate ? infoData.getDate() : null);


        if (!keyUpdate) {

            info.setPriceTypeId(Integer.parseInt(priceType.getPriceTypeId()));
            info.setPriceTypeIdItem(priceType.getPriceTypeItemId());
            info.setCreatorUserId(priceType.getPriceCreatorUserId());
            info.setCreationDate(priceType.getPriceCreationDate());

        }else{

            info.setPriceTypeId(infoData.getPriceTypeId());
            info.setPriceTypeIdItem(infoData.getPriceTypeIdItem());
            info.setCreatorUserId(infoData.getCreatorUserId());
            info.setCreationDate(infoData.getCreationDate());
        }




        OperationResult result = InfoRepository.getInstance().AddPrice(info, id);
        if (result.IsSuccess) {
            if (!keyUpdate)
                DialogSuccess("فاکتور مورد نظر با موفقیت اضافه شد.", getContext());
            else
                DialogSuccess("ویرایش فاکتور مورد نظر با موفقیت انجام شد.", getContext());
            navController.navigate(R.id.action_addExpensesFragment_to_mainFragment);
        } else {
            DialogFailed(ResultMessage.ErrorMessage, getContext());

        }

    }


    private void OpenPriceTypeDialog() {
        DialogFragmentPriceType dialog = new DialogFragmentPriceType();
        dialog.setTargetFragment(AddExpensesFragment.this, 1);
        dialog.show(getFragmentManager(), getString(R.string.app_name));


    }


    @Override
    public void GetPrice() {

        if (
                preferences.contains("getPriceTypeItemId" )
                && preferences.contains("getPriceTypeName")
                && preferences.contains("getPriceTypeId") ) {

            priceType=new PriceType();
            priceType.setPriceTypeItemId(Integer.parseInt(preferences.getString("getPriceTypeItemId", "0")));
            priceType.setPriceTypeId(preferences.getString("getPriceTypeId", ""));
            priceType.setPriceTypeName(preferences.getString("getPriceTypeName", ""));

            priceType.setPriceCreatorUserId(UserInformations.getUserId());
            Calendar calendar=Calendar.getInstance();
            priceType.setPriceCreationDate(calendar.getTime()+"");


            fillPriceType(priceType);
        }
    }

    private void fillPriceType(PriceType priceType) {
        if (priceType != null) {
            txtPriceTypeResult.setVisibility(View.VISIBLE);
            txtPriceTypeResult.setText(priceType.getPriceTypeName() + "");
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.txt_price_type_result:
                removePriceType();
                break;
        }
        return false;
    }

    private  void removePriceType(){


        txtPriceTypeResult.setVisibility(View.GONE);
        txtPriceTypeResult.setText("");

    }
}
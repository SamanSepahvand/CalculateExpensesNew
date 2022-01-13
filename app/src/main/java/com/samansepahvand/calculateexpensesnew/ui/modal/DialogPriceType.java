package com.samansepahvand.calculateexpensesnew.ui.modal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.samansepahvand.calculateexpensesnew.MainApplication;
import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;
import com.samansepahvand.calculateexpensesnew.infrastructure.expandableListView.Group;
import com.samansepahvand.calculateexpensesnew.ui.adapter.MyExpandableListAdapter;


public class DialogPriceType extends Dialog implements View.OnClickListener {


    private Context mContext;

    private Button btnConfirm, btnCancel;

    private ImageView imgCloseDialog;


    private OnAcceptInterface acceptListener;
    private OnCancelInterface cancelListener;


    public DialogPriceType(@NonNull Context context, boolean hasConfirm , boolean hasCancel) {
        super(context);
        this.mContext = context;
        setContentView(R.layout.dialog_dialog_price_type);
        this.getWindow().getAttributes().windowAnimations = R.style.AlertDialogAnimation;
       // getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView(hasConfirm,hasCancel);
        // initValue(alertDialogModel);


    }

    private void OpenDialogChoosePriceType() {
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter((Activity) mContext,
                createData());
        listView.setAdapter(adapter);

    }

    public SparseArray<Group> createData() {

        SparseArray<Group> groups = new SparseArray<Group>();

        for (int j = 0; j < 5; j++) {
            Group group = new Group("Test " + j);
            for (int i = 0; i < 5; i++) {
                group.children.add("Sub Item" + i);
            }
            groups.append(j, group);
        }
        return groups;
    }



    private void initView(final boolean hasConfirm , final boolean hasCancel) {


        btnConfirm = findViewById(R.id.btn_dialog_confirm);
        btnCancel = findViewById(R.id.btn_dialog_cancel);
        imgCloseDialog = findViewById(R.id.img_dialog_close);


        btnConfirm.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                btnConfirm.startAnimation(MainApplication.SetAnimation("FadeIn"));
                // btnCancel.startAnimation(MainApplication.SetAnimation("FadeIn"));


                if (hasCancel) btnCancel.setVisibility(View.VISIBLE);
                if (hasConfirm) btnConfirm.setVisibility(View.VISIBLE);

               // txtBody.setVisibility(View.VISIBLE);



            }
        }, Constants.DelayMoreFast);

        OpenDialogChoosePriceType();
        imgCloseDialog.startAnimation(MainApplication.SetAnimation("Rotate"));
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imgCloseDialog.setOnClickListener(this);


    }

//    private void initValue(AlertDialogModel dialog) {
//
//        if (dialog.getActionConfirm().equals("")) {
//            btnConfirm.setVisibility(View.GONE);
//        } else {
//            btnConfirm.setText(dialog.getActionConfirm());
//        }
//
//        if (dialog.getActionCancel().equals("")) {
//            btnCancel.setVisibility(View.GONE);
//        } else {
//            btnCancel.setText(dialog.getActionCancel());
//        }
//
//
//        txtBody.setText(dialog.getContent());
//
//    }


    public Dialog setAcceptButton(OnAcceptInterface listener) {
        this.acceptListener = listener;
        // btnConfirm.setVisibility(View.VISIBLE);
        return this;
    }


    public Dialog setAc(OnAcceptInterface listener) {
        this.acceptListener = listener;
        // btnConfirm.setVisibility(View.VISIBLE);
        return this;
    }


    public Dialog setCancelButton(OnCancelInterface cancelListener) {
        this.cancelListener = cancelListener;
        //btnCancel.setVisibility(View.VISIBLE);
        return this;
    }


    public Dialog setButtonConfirmCustom(String title, int alertButtonStyle) {
        //  this.btnConfirm.setVisibility(View.VISIBLE);
        this.btnConfirm.setText(title);
        this.btnConfirm.setBackgroundResource(alertButtonStyle);
        return this;
    }


    public Dialog setTextCancelButtonCustom(String title) {
        //  this.btnCancel.setVisibility(View.VISIBLE);
        this.btnCancel.setText(title);
        return this;
    }


    public Dialog setForceAction(boolean status) {
        if (status)   this.imgCloseDialog.setEnabled(false);
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
                dismiss();
                break;
            case R.id.btn_dialog_confirm:
                if (acceptListener != null)
                    acceptListener.accept();
                dismiss();
                break;


        }

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
        void accept();
    }

    public interface OnCancelInterface {
        void cancel();
    }
}

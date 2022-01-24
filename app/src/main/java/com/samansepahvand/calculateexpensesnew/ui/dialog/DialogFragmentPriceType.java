package com.samansepahvand.calculateexpensesnew.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.samansepahvand.calculateexpensesnew.MainApplication;
import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.business.metamodel.UserInformations;
import com.samansepahvand.calculateexpensesnew.business.repository.PriceTypeRepository;
import com.samansepahvand.calculateexpensesnew.db.PriceType;
import com.samansepahvand.calculateexpensesnew.infrastructure.expandableListView.PriceTypeHeader;
import com.samansepahvand.calculateexpensesnew.ui.adapter.MyExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;


public class DialogFragmentPriceType extends DialogFragment implements View.OnClickListener, View.OnLongClickListener , MyExpandableListAdapter.IGetPriceType{

    private static final String TAG = "DialogPriceType";


    private Context mContext = getActivity();

    private Button btnConfirm, btnCancel;

    private ImageView imgCloseDialog;


    private ConstraintLayout clPriceTypeFilterShow;
    private View view;


    private OnAcceptInterface acceptListener;
    private OnCancelInterface cancelListener;

    private ExpandableListView listView;

    private  List<PriceType> priceTypeList = new ArrayList<>();

    private MyExpandableListAdapter.IGetPriceType _iGetPriceType;
    private IPriceTypeNew  _priceType;



    @Override
    public void GetPriceType(PriceType priceType) {

        _priceType.GetPrice(priceType);
        getDialog().dismiss();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_dialog_price_type, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView(view);
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments()!=null){
            PriceType priceType=(PriceType) getArguments().getSerializable("PriceType");
            checkDuplicatePrice(priceType);


        }
    }




    private  void checkDuplicatePrice(PriceType priceType) {

        if (priceTypeList != null && priceTypeList.size() != 0) {
            for (PriceType item : priceTypeList) {
                if (priceType.getPriceTypeItemId() != priceType.getPriceTypeItemId()) {
                    priceTypeList.add(item);
                    break;
                }
            }
        }else{
            priceTypeList.add(priceType);
        }
        filterCount(priceTypeList);


    }

    private void filterCount(List<PriceType> priceTypeList) {
        if (priceTypeList != null && priceTypeList.size() != 0) {
               clPriceTypeFilterShow.setVisibility(View.VISIBLE);
//               txtPriceTypeCount.setText(priceTypeList.size() + "");
        }else{
               clPriceTypeFilterShow.setVisibility(View.GONE);
        }



    }

    private void deleteFilterCount() {
        if (priceTypeList != null && priceTypeList.size() != 0) {
            priceTypeList.clear();
//            txtPriceTypeCount.setText("0");
        }
    }



    public void OpenDialogChoosePriceType() {

        OperationResult<SparseArray<PriceTypeHeader>> result = PriceTypeRepository.getInstance().GetPriceTypeCategory(UserInformations.getUserId());
        if (result.IsSuccess) {


            MyExpandableListAdapter adapter = new MyExpandableListAdapter(getActivity(),
                    result.Item,_iGetPriceType );

            listView.setAdapter(adapter);

        } else {
            Toast.makeText(mContext, result.Message, Toast.LENGTH_SHORT).show();
        }


    }

    private void initView(View view) {

        listView = (ExpandableListView) view.findViewById(R.id.listView);
        _iGetPriceType=(MyExpandableListAdapter.IGetPriceType) this;
        btnConfirm = view.findViewById(R.id.btn_dialog_confirm);
        btnCancel = view.findViewById(R.id.btn_dialog_cancel);
        imgCloseDialog = view.findViewById(R.id.img_dialog_close);

        clPriceTypeFilterShow = view.findViewById(R.id.cl_pricetype_filter_show);





        btnConfirm.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnConfirm.startAnimation(MainApplication.SetAnimation("FadeIn"));
                btnCancel.startAnimation(MainApplication.SetAnimation("FadeIn"));

//                if (hasCancel) btnCancel.setVisibility(View.VISIBLE);
//                if (hasConfirm) btnConfirm.setVisibility(View.VISIBLE);
                // txtBody.setVisibility(View.VISIBLE);
            }
        }, Constants.DelayMoreFast);


        OpenDialogChoosePriceType();
        imgCloseDialog.startAnimation(MainApplication.SetAnimation("Rotate"));
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imgCloseDialog.setOnClickListener(this);
        clPriceTypeFilterShow.setOnClickListener(this);


    }

    @Override
    public void onAttach(@NonNull Context context) {

        try{
            _priceType=(IPriceTypeNew) getTargetFragment();
        }catch (ClassCastException  e){

            Log.e(TAG, "onAttach: "+e.getMessage() );
        }

        super.onAttach(context);
    }

    public interface  IPriceTypeNew{
        void GetPrice(PriceType priceType);
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

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
            case R.id.cl_pricetype_filter_show:
                DialogAddPriceType dialogAddPriceType=new DialogAddPriceType(getContext(),true,true);
                dialogAddPriceType.setCancelable(true);
                dialogAddPriceType.show();
                dismiss();

                break;


        }

    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.img_pricetype_delete:
                deleteFilterCount();
                break;

        }
        return false;
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

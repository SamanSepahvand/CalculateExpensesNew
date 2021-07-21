package com.samansepahvand.calculateexpensesnew.ui.fragment;

import android.animation.ObjectAnimator;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.samansepahvand.calculateexpensesnew.MainApplication;
import com.samansepahvand.calculateexpensesnew.R;


public class MainFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //component
    private CardView btnShowPrice, btnAdd;
    private NavController mNavController;
    private ImageView imgInfo;
    private BottomSheetBehavior sheetBehavior;
    private ConstraintLayout bottomSheet;

    private ConstraintLayout clShadow;

    private ImageView imgMoreAdd,imgMoreList;


    public MainFragment() {

    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        return inflater.inflate(R.layout.fragment_main_main, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiView(view);

    }

    private void intiView(View view) {
        mNavController = Navigation.findNavController(view);

        btnShowPrice = view.findViewById(R.id.btn_show);
        btnAdd = view.findViewById(R.id.btn_add);

        imgMoreAdd = view.findViewById(R.id.img_more_add);
        imgMoreList = view.findViewById(R.id.img_more_list);

        imgInfo = view.findViewById(R.id.img_info);
        clShadow = view.findViewById(R.id.shadow);
        clShadow.setVisibility(View.GONE);

        bottomSheet = view.findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {


                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //  txtTest.setText("Close Sheet");
                        clShadow.setAnimation(MainApplication.SetAnimation("FadeIn"));
                        clShadow.setVisibility(View.VISIBLE);


                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        clShadow.setAnimation(MainApplication.SetAnimation("FadeOut"));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                clShadow.setVisibility(View.GONE);
                            }
                        }, 300);

                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        imgInfo.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnShowPrice.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_show:
                rotateImage(imgMoreList);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mNavController.navigate(R.id.action_mainFragment_to_listExpensesFragment);
                    }
                },400);
                break;
            case R.id.btn_add:
                rotateImage(imgMoreAdd);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mNavController.navigate(R.id.action_mainFragment_to_addExpensesFragment);
                    }
                },300);


                break;

            case R.id.img_info:

                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
        }

    }

    private void rotateImage(ImageView imageView) {
        ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(imageView ,
                "rotation", 0f, 90f);
        imageViewObjectAnimator.setDuration(200); // miliseconds
        imageViewObjectAnimator.start();



    }


}
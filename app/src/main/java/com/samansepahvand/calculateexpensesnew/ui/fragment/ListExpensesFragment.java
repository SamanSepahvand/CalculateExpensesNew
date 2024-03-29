package com.samansepahvand.calculateexpensesnew.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;
import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.business.metamodel.UserInformations;
import com.samansepahvand.calculateexpensesnew.business.repository.InfoRepository;
import com.samansepahvand.calculateexpensesnew.db.Info;
import com.samansepahvand.calculateexpensesnew.helper.interfaces.ActionInfo;
import com.samansepahvand.calculateexpensesnew.infrastructure.Utility;
import com.samansepahvand.calculateexpensesnew.ui.adapter.ItemTouchHelperCallback;
import com.samansepahvand.calculateexpensesnew.ui.adapter.MainRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListExpensesFragment extends Fragment implements View.OnClickListener, ActionInfo, SearchView.OnQueryTextListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private TextView txtTotalPrice,txtInvoiceCount,txtFullName;
    private MainRecyclerAdapter showAdapter;
    private ItemTouchHelperExtension mItemTouchHelper;
    private ItemTouchHelperCallback mCallback;
    private NavController navController;
    private SearchView searchView;
    private ImageView imgBack;


    public ListExpensesFragment() {
        // Required empty public constructor
    }

    public static ListExpensesFragment newInstance(String param1, String param2) {
        ListExpensesFragment fragment = new ListExpensesFragment();
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
        return inflater.inflate(R.layout.fragment_list_expenses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        intiView(view);

    }

    private void intiView(View view) {
        navController = Navigation.findNavController(view);

        txtFullName=view.findViewById(R.id.txt_full_name);

        imgBack=view.findViewById(R.id.img_back);

        searchView=view.findViewById(R.id.search_view);

        TextView textView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        textView.setTextSize(12);
        textView.setTypeface(Constants.CustomStyleElement());



        txtInvoiceCount=view.findViewById(R.id.txt_count);
        txtTotalPrice = view.findViewById(R.id.txt_total_price);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        showAdapter = new MainRecyclerAdapter(getContext());
        recyclerView.setAdapter(showAdapter);
        initData();

        searchView.setOnQueryTextListener(this);
        imgBack.setOnClickListener(this);

        txtFullName.setText(UserInformations.getFullName());
    }

    private void SupplierProductDeliveryData(final List<Info> items) {

        items.add(DammyData());
        txtInvoiceCount.setText("تعداد: "+(items.size()-1)+"");
        showAdapter.updateData(items);
        mCallback = new ItemTouchHelperCallback();
        mItemTouchHelper = new ItemTouchHelperExtension(mCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.smoothScrollToPosition(0);
    }


    private Info DammyData() {
        Info deliveryMetaModel = new Info();
        deliveryMetaModel.setDate("");
        deliveryMetaModel.setPrice(0);
        deliveryMetaModel.setTitle("633325632");
        return deliveryMetaModel;
    }


    private void initData() {
        OperationResult result = InfoRepository.getInstance().GetInfo();
        if (result.IsSuccess) {
            SupplierProductDeliveryData(result.Items);
            txtTotalPrice.setText(Utility.splitDigits(Integer.parseInt(result.Message)));
        } else {
            Toast.makeText(getContext(), "" + result.Message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void actionInfo(Info info) {
        if (info != null) {
//            Intent intent = new Intent(ShowActivity.this, AddActivity.class);
//            intent.putExtra("Info", info);
//            intent.putExtra("Id", info.getId());
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
        }


    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public boolean onQueryTextChange(String s) {
        int countShow = 0;
        OperationResult<Info> result = InfoRepository.getInstance().GetInfo();
        List<Info> newList = new ArrayList<>();
        for (Info info : result.Items ) {
            if (info.getTitle().toLowerCase().contains(s)) {
                newList.add(info);
            } else {
            }
            countShow++;
            showAdapter.updateData(newList);

        }
        return true;
    }



    @Override
    public void
    actionDelete(Info info) {

        if (info!=null){

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.img_back:
                navController.popBackStack();
                break;
        }
    }
}
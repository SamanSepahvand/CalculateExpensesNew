package com.samansepahvand.calculateexpensesnew.ui.fragment;

import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.DialogFailed;
import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.splitDigits;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;
import com.samansepahvand.calculateexpensesnew.business.metamodel.InfoMetaModel;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.business.repository.AccountRepository;
import com.samansepahvand.calculateexpensesnew.business.repository.InfoRepository;
import com.samansepahvand.calculateexpensesnew.db.Info;
import com.samansepahvand.calculateexpensesnew.ui.adapter.SameInvoiceAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvoiceDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoiceDetailsFragment extends Fragment implements View.OnClickListener, SameInvoiceAdapter.IGetSomeInfoMeta {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private NavController navController;

    private TextView txtInvoiceTitle, txtInvoicePriceTypeFullName,
            txtInvoicePriceValue, txtInvoiceCreatorFullName, txtCreationFullDate, txtEstimatedDate;
    private Button btnInvoiceShare;
    private ImageView imgBack,imgMainLogoPriceType;
    private InfoMetaModel infoMetaModel=new InfoMetaModel();
    private RecyclerView recyclerviewSameInvoices;

    private SameInvoiceAdapter.IGetSomeInfoMeta _iGetSomeInfoMeta;

    public InvoiceDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InvoiceDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoiceDetailsFragment newInstance(String param1, String param2) {
        InvoiceDetailsFragment fragment = new InvoiceDetailsFragment();
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
        return inflater.inflate(R.layout.fragment_invoice_details, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        initView(view);
        initData();
        initRecyclerView();


    }

    private void initView(View view) {
        txtInvoiceTitle = view.findViewById(R.id.txt_title);
        txtInvoicePriceTypeFullName = view.findViewById(R.id.txt_price_type_full_name);
        txtInvoicePriceValue = view.findViewById(R.id.txt_value_price);
        txtInvoiceCreatorFullName = view.findViewById(R.id.txt_creator_full_name);
        txtCreationFullDate = view.findViewById(R.id.txt_create_date);
        txtEstimatedDate = view.findViewById(R.id.txt_estimate_date);
        btnInvoiceShare = view.findViewById(R.id.btn_share);
        imgBack = view.findViewById(R.id.img_back);
        imgMainLogoPriceType=view.findViewById(R.id.img_main_logo_price_type);

        _iGetSomeInfoMeta=(SameInvoiceAdapter.IGetSomeInfoMeta)this;
        recyclerviewSameInvoices = view.findViewById(R.id.recyclerview_same_product);


        imgBack.setOnClickListener(this);
        btnInvoiceShare.setOnClickListener(this);
    }

    private void initRecyclerView() {

        recyclerviewSameInvoices.setHasFixedSize(true);
        recyclerviewSameInvoices.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerviewSameInvoices.setAdapter(new SameInvoiceAdapter(getActivity(), getSameInvoices(),_iGetSomeInfoMeta));
    }

    private List<InfoMetaModel> getSameInvoices() {
        OperationResult<InfoMetaModel> result = InfoRepository.getInstance().GetSameInvoices(infoMetaModel);
        if (result.IsSuccess){
            return result.Items;
        }else{
            DialogFailed(result.Message,getActivity());
            return null;
        }

    }

    private void initData() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            infoMetaModel = (InfoMetaModel) bundle.getSerializable("InfoMetaModel");
            txtInvoiceTitle.setText(infoMetaModel.getTitle());


            txtInvoicePriceTypeFullName.setText(infoMetaModel.getPriceTypeName() + " » " + infoMetaModel.getPriceTypeItemName());

            String str = "<font color=red><b>" +
                    splitDigits(infoMetaModel.getPrice()) +
                    "</b></font>"
                    + "  ریال ";
            Spanned strHtml = Html.fromHtml(str);
            txtInvoicePriceValue.setText(strHtml);


            txtInvoiceCreatorFullName.setText(getFullName(infoMetaModel.getCreatorUserId()));
            txtCreationFullDate.setText(infoMetaModel.getFarsiDate());
            txtEstimatedDate.setText(infoMetaModel.getEstimateDate());
            imgMainLogoPriceType.setImageResource(Constants.PriceTypeHeaderPicture[infoMetaModel.getPriceTypeId()]);


        }

    }

    private String getFullName(int creatorUserId) {
        return AccountRepository.getInstance().getUserById(creatorUserId);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_share:
                /// do some things stuff
                break;

            case R.id.img_back:
                navController.popBackStack();
                break;
        }
    }

    @Override
    public void GetSomeInfoMeta(InfoMetaModel infoMetaModel,String typeShow) {
        if (typeShow.equals("Single")){


            InvoiceDetailsFragmentDirections.ActionInvoiceDetailsFragmentSelf action=
                    InvoiceDetailsFragmentDirections.actionInvoiceDetailsFragmentSelf();

            action.setInfoMetaModel(infoMetaModel);
            navController.navigate(action);

        }else if (typeShow.equals("All")){
            navController.navigate(R.id.action_invoiceDetailsFragment_to_listExpensesFragment);

        }else{
            /////
        }


    }



}
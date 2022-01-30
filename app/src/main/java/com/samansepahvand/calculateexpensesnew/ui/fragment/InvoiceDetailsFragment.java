package com.samansepahvand.calculateexpensesnew.ui.fragment;

import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.DialogFailed;
import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.splitDigits;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
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

    private TextView txtInvoiceListSame,txtInvoiceTitle, txtInvoicePriceTypeFullName,
            txtInvoicePriceValue, txtInvoiceCreatorFullName, txtCreationFullDate, txtEstimatedDate;
    private Button btnInvoiceShare;
    private ImageView imgBack,imgMainLogoPriceType;
    private InfoMetaModel infoMetaModel=new InfoMetaModel();
    private RecyclerView recyclerviewSameInvoices;

    private SameInvoiceAdapter.IGetSomeInfoMeta _iGetSomeInfoMeta;
    private static final int REQUEST_EXTERNAL_STORAGe = 1;
    private static String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

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

        verifystoragepermissions(getActivity());


        txtInvoiceTitle = view.findViewById(R.id.txt_title);
        txtInvoicePriceTypeFullName = view.findViewById(R.id.txt_price_type_full_name);
        txtInvoicePriceValue = view.findViewById(R.id.txt_value_price);
        txtInvoiceCreatorFullName = view.findViewById(R.id.txt_creator_full_name);
        txtCreationFullDate = view.findViewById(R.id.txt_create_date);
        txtEstimatedDate = view.findViewById(R.id.txt_estimate_date);
        btnInvoiceShare = view.findViewById(R.id.btn_share);
        imgBack = view.findViewById(R.id.img_back);
        imgMainLogoPriceType=view.findViewById(R.id.img_main_logo_price_type);

        txtInvoiceListSame=view.findViewById(R.id.txt_no_same_invoice);
         _iGetSomeInfoMeta=(SameInvoiceAdapter.IGetSomeInfoMeta)this;
        recyclerviewSameInvoices = view.findViewById(R.id.recyclerview_same_product);


        imgBack.setOnClickListener(this);
        btnInvoiceShare.setOnClickListener(this);
    }

    private void initRecyclerView() {

        recyclerviewSameInvoices.setHasFixedSize(true);

        recyclerviewSameInvoices.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        OperationResult<InfoMetaModel> result = InfoRepository.getInstance().GetSameInvoices(infoMetaModel);
        if (result.IsSuccess){
            recyclerviewSameInvoices.setVisibility(View.VISIBLE);
            txtInvoiceListSame.setVisibility(View.GONE);
            recyclerviewSameInvoices.setAdapter(new SameInvoiceAdapter(getActivity(), result.Items,_iGetSomeInfoMeta));
        }else{
            txtInvoiceListSame.setVisibility(View.VISIBLE);
            recyclerviewSameInvoices.setVisibility(View.GONE);

          //  DialogFailed(result.Message,getActivity());
            return ;
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

            txtInvoiceListSame.setCompoundDrawablesWithIntrinsicBounds(0,Constants.PriceTypeHeaderPicture[infoMetaModel.getPriceTypeId()],0,0);
            Spanned strHtml1 = Html.fromHtml("فاکتور مشابه ای در دسته بندی "+
                    "<font color=red><b>" +infoMetaModel.getPriceTypeName()
                    + "</b></font>" +" وجود ندارد  ");
            txtInvoiceListSame.setText(strHtml1);


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

                screenshot(getActivity().getWindow().getDecorView().getRootView(), "result");
                break;

            case R.id.img_back:
                navController.popBackStack();
                break;



        }
    }

    protected static File screenshot(View view, String filename) {
        Date date = new Date();

        // Here we are initialising the format of our image name
        CharSequence format = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        try {
            // Initialising the directory of storage
            String dirpath = Environment.getExternalStorageDirectory() + "";
            File file = new File(dirpath);
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
            }

            // File name
            String path = dirpath + "/" + filename + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            File imageurl = new File(path);
            FileOutputStream outputStream = new FileOutputStream(imageurl);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            outputStream.flush();
            outputStream.close();
            return imageurl;

        } catch (FileNotFoundException io) {
            io.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // verifying if storage permission is given or not
    public static void verifystoragepermissions(Activity activity) {

        int permissions = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // If storage permission is not given then request for External Storage Permission
        if (permissions != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, permissionstorage, REQUEST_EXTERNAL_STORAGe);
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
package com.samansepahvand.calculateexpensesnew.ui.adapter;

import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.splitDigits;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;
import com.samansepahvand.calculateexpensesnew.business.metamodel.InfoMetaModel;

import java.util.ArrayList;
import java.util.List;

public class SameInvoiceAdapter extends RecyclerView.Adapter<SameInvoiceAdapter.MyViewHolder> {




    private Context context;
    private List<InfoMetaModel> infoMetaModelList=new ArrayList<>();
    private IGetSomeInfoMeta _iGetSomeInfoMeta;

    public SameInvoiceAdapter(Context context, List<InfoMetaModel> infoMetaModelList,IGetSomeInfoMeta infoMeta) {
        this.context = context;
        this.infoMetaModelList = infoMetaModelList;
        this._iGetSomeInfoMeta=infoMeta;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        view= LayoutInflater.from(context).inflate(R.layout.item_some_invoices,parent,false);
        view.setRotationY(180);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        InfoMetaModel infoMetaModel=infoMetaModelList.get(position);
        FillData(infoMetaModel,position,holder);
    }

    private void FillData(InfoMetaModel infoMetaModel, int position,MyViewHolder holder) {

        if (position==0){
            holder.txtPrice.setText(infoMetaModel.getTitle()+"");
            holder.txtPrice.setTextColor(ContextCompat.getColor(context,R.color.newRedDark));
            holder.clBackground.setBackgroundResource(R.drawable.transparent);
            holder.imgPicture.setImageResource(Constants.PriceTypeHeaderPicture[infoMetaModel.getPriceTypeId()]);
            holder.imgPicture.getLayoutParams().height = 180;
            holder.imgPicture.getLayoutParams().width = 180;
            holder.txtTitle.setText("");


        }else if (position== infoMetaModelList.size()-1){
         //   holder.txtPrice.setText(infoMetaModel.getPrice()+"");
            holder.imgPicture.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
            holder.txtTitle.setText(infoMetaModel.getTitle());
            holder.txtTitle.setTextColor(ContextCompat.getColor(context,R.color.black));
            holder.txtPrice.setText("");


        }else{


            String str = "<font color=red><b>" +
                    splitDigits(infoMetaModel.getPrice()) +
                    "</b></font>"
                    + "  ریال ";
            Spanned strHtml = Html.fromHtml(str);

            holder.txtPrice.setText(strHtml);
            holder.txtTitle.setText(infoMetaModel.getTitle());
            holder.imgPicture.setImageResource(Constants.PriceTypeHeaderPicture[infoMetaModel.getPriceTypeId()]);


        }

    }

    @Override
    public int getItemCount() {
        return infoMetaModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout clBackground;
        private TextView txtTitle,txtPrice;
        private ImageView imgPicture;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            clBackground=itemView.findViewById(R.id.cl_background);
            txtTitle=itemView.findViewById(R.id.txt_title);
            txtPrice=itemView.findViewById(R.id.txt_price);
            imgPicture=itemView.findViewById(R.id.img_picture);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getAdapterPosition()!=0  && getAdapterPosition()!=infoMetaModelList.size()-1){
                        _iGetSomeInfoMeta.GetSomeInfoMeta(infoMetaModelList.get(getAdapterPosition()),"Single");
                    }else{
                        _iGetSomeInfoMeta.GetSomeInfoMeta(infoMetaModelList.get(getAdapterPosition()),"All");
                    }
                }
            });

        }
    }
    public  interface IGetSomeInfoMeta{

       void   GetSomeInfoMeta (InfoMetaModel infoMetaModel,String typeShow);

    }
}

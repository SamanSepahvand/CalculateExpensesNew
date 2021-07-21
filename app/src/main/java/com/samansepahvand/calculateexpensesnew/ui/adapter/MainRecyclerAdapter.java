package com.samansepahvand.calculateexpensesnew.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.loopeer.itemtouchhelperextension.Extension;
import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.db.Info;
import com.samansepahvand.calculateexpensesnew.helper.interfaces.ActionInfo;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.splitDigits;


public class MainRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_RECYCLER_WIDTH = 1000;
    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    public static final int ITEM_TYPE_ACTION_WIDTH_NO_SPRING = 1002;
    private List<Info> mDatas;
    private Context mContext;


    private ActionInfo actionInfo;

    public MainRecyclerAdapter(Context context) {
        mDatas = new ArrayList<>();
        mContext = context;
        actionInfo = (ActionInfo) context;
    }

    public void setDatas(List<Info> datas) {
        mDatas.clear();
        if (datas != null)
            mDatas.addAll(datas);
    }

    public void updateData(List<Info> datas) {
        setDatas(datas);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout._item_main_show_all_price, parent, false);
        return new ItemSwipeWithActionWidthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {


//        if (holder instanceof ItemSwipeWithActionWidthViewHolder) {
        ItemSwipeWithActionWidthViewHolder viewHolder = (ItemSwipeWithActionWidthViewHolder) holder;
        viewHolder.itemView.setSelected(1 == position);

        viewHolder.mActionViewRefresh.setSelected(true);

        viewHolder.mActionViewRefresh.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }

        );
        viewHolder.mActionViewDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {

                        } catch (Exception e) {

                        }
                    }
                }

        );

        ItemBaseViewHolder baseViewHolder = (ItemBaseViewHolder) holder;
        baseViewHolder.bind(mDatas.get(position));
        baseViewHolder.mViewContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


//        }
    }


    private void doDelete(int adapterPosition) {
        //  Toast.makeText(mContext, "" + mDatas.get(adapterPosition).getId(), Toast.LENGTH_SHORT).show();
  //      Dialog(adapterPosition, mDatas.get(adapterPosition));

    }


//    private void Dialog(int position, DeliveryMetaModel item) {
//        final AlertDialogModal modal2 = new AlertDialogModal(mContext, true, true);
//        modal2.setImageTypeCustom(Constants.TypeImageAlertDialog[0]);
//        modal2.setButtonConfirmCustom("حذف نهایی", Constants.TypeButtonStyleAlertDialog[2]);
//        modal2.setTextContent(Constants.ContentConfirmDelete(item.getProductName(), item.getProductCode() + ""));
//        modal2.setAcceptButton(new AlertDialogModal.OnAcceptInterface() {
//            @Override
//            public void accept() {
//                MessageResult result = ProductDeliveryRepository.getInstance().DeleteSingleDeliveryItems(mDatas.get(position).getId());
//                MessageResultMethod(position, result.getContent(), result.getTypeDialog(), result.isState());
//                return;
//            }
//        });
//        modal2.setCancelButton(new AlertDialogModal.OnCancelInterface() {
//            @Override
//            public void cancel() {
//                modal2.dismiss();
//                return;
//            }
//        });
//        modal2.show();
//    }
//
//    private void MessageResultMethod(int position, String content, int typeAlert, boolean state) {
//        Log.e("TAG", "MessageResultMethod: " + state);
//        final AlertDialogModal modal2 = new AlertDialogModal(mContext, true, false);
//        modal2.setImageTypeCustom(typeAlert == Enumerations.AlertDialogType.Error ? Constants.TypeImageAlertDialog[2] : Constants.TypeImageAlertDialog[3]);
//        modal2.setButtonConfirmCustom("بستن", typeAlert == Enumerations.AlertDialogType.Error ? Constants.TypeButtonStyleAlertDialog[2] : Constants.TypeButtonStyleAlertDialog[4]);
//        modal2.setTextContent(content);
//        modal2.setAcceptButton(new AlertDialogModal.OnAcceptInterface() {
//            @Override
//            public void accept() {
//                if (state == true) {
//                    mDatas.remove(position);
//                    //Call method for delete Delivery Single
//                    notifyItemRemoved(position);
//                    interfaceRefresh.refresh(true);
//                }
//
//                return;
//            }
//        });
//
//        modal2.show();
//
//
//    }


    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_ACTION_WIDTH;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ItemBaseViewHolder extends RecyclerView.ViewHolder {
        View mViewContent;
        View mActionContainer;
        private TextView txtTitle,txtPrice,txtDate,txtRow;

        private FrameLayout root;

        public ItemBaseViewHolder(View itemView) {
            super(itemView);
            initAdapterView(itemView);

        }

        private void initAdapterView(View view) {

            txtDate=itemView.findViewById(R.id.item_txt_date);
            txtTitle=itemView.findViewById(R.id.item_txt_title);
            txtPrice=itemView.findViewById(R.id.item_txt_price);
            txtRow=itemView.findViewById(R.id.item_txt_row);

            root = itemView.findViewById(R.id.root);
            mViewContent = itemView.findViewById(R.id.view_list_main_content);
            mActionContainer = itemView.findViewById(R.id.view_list_repo_action_container);

            mActionContainer.setSelected(true);
        }

        public void bind(Info testModel) {
            setProductDeliveryItems(testModel, getAdapterPosition());

        }


        private void setProductDeliveryItems(Info info, int position) {


            if (info.getTitle().equals("633325632")){
                root.setVisibility(View.INVISIBLE);
                mViewContent.setVisibility(View.INVISIBLE);
                mActionContainer.setVisibility(View.INVISIBLE);
                root.setSelected(true);
            } else {
                txtRow.setText(String.valueOf(position + 1));

                txtDate.setText("تاریخ ثبت : "+info.getDate());
                txtTitle.setText(info.getTitle());
                String str="<font color=red><b>"+
                        splitDigits(info.getPrice())+
                "</b></font>"
                        +"  ریال ";
                Spanned strHtml = Html.fromHtml(str);
                txtPrice.setText(strHtml);


            }


        }
    }


    class ItemSwipeWithActionWidthViewHolder extends ItemBaseViewHolder implements Extension {

        View mActionViewDelete;
        View mActionViewRefresh;

        public ItemSwipeWithActionWidthViewHolder(View itemView) {
            super(itemView);
            mActionViewDelete = itemView.findViewById(R.id.view_list_repo_action_delete);
            mActionViewRefresh = itemView.findViewById(R.id.view_list_repo_action_update);
        }

        @Override
        public float getActionWidth() {
            return mActionContainer.getWidth();
        }
    }

    class ItemSwipeWithActionWidthNoSpringViewHolder extends ItemSwipeWithActionWidthViewHolder implements Extension {

        public ItemSwipeWithActionWidthNoSpringViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public float getActionWidth() {
            return mActionContainer.getWidth();
        }
    }


}

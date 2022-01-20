package com.samansepahvand.calculateexpensesnew.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.loopeer.itemtouchhelperextension.Extension;
import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;
import com.samansepahvand.calculateexpensesnew.business.metamodel.InfoMetaModel;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.business.metamodel.ResultMessage;
import com.samansepahvand.calculateexpensesnew.business.repository.InfoRepository;
import com.samansepahvand.calculateexpensesnew.db.Info;
import com.samansepahvand.calculateexpensesnew.helper.interfaces.ActionInfo;
import com.samansepahvand.calculateexpensesnew.ui.modal.AlertDialogModal;

import java.util.ArrayList;
import java.util.List;

import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.DialogFailed;
import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.DialogSuccess;
import static com.samansepahvand.calculateexpensesnew.infrastructure.Utility.splitDigits;


public class MainRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_RECYCLER_WIDTH = 1000;
    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    public static final int ITEM_TYPE_ACTION_WIDTH_NO_SPRING = 1002;
    private List<InfoMetaModel> mDatas;
    private Context mContext;


    private ActionInfo actionInfo;

    public MainRecyclerAdapter(Context context) {
        mDatas = new ArrayList<>();
        mContext = context;
        actionInfo = (ActionInfo) context;
    }

    public void setDatas(List<InfoMetaModel> datas) {
        mDatas.clear();
        if (datas != null)
            mDatas.addAll(datas);
    }

    public void updateData(List<InfoMetaModel> datas) {
        setDatas(datas);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout._item_main_show_all_price, parent, false);
        return new ItemSwipeWithActionWidthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


//        if (holder instanceof ItemSwipeWithActionWidthViewHolder) {
        ItemSwipeWithActionWidthViewHolder viewHolder = (ItemSwipeWithActionWidthViewHolder) holder;
        viewHolder.itemView.setSelected(1 == position);

        viewHolder.mActionViewUpdate.setSelected(true);

        viewHolder.mActionViewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {





                        OperationResult<Info> result = InfoRepository.getInstance().GetInfoByMeta(mDatas.get(position),"Show");
                        actionInfo.actionInfo(result.Item);





                    }
                }

        );
        viewHolder.mActionViewDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        OperationResult<Info> result = InfoRepository.getInstance().GetInfoByMeta(mDatas.get(position),"Delete");

                        if (result.IsSuccess) {
                            mDatas.remove(position);
                            notifyItemRemoved(position);
                            DialogSuccess(ResultMessage.SuccessMessage,mContext);
                            // navController.navigate(MainFragmentDirections.actionMainFragmentToListExpensesFragment());
                        } else {
                            DialogFailed("خطا در حذف فاکتور مورد نظر !",mContext);
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
        private TextView txtTitle, txtPrice, txtDate, txtRow,txtEstimateTime,txtPriceType;

        private FrameLayout root;

        public ItemBaseViewHolder(View itemView) {
            super(itemView);
            initAdapterView(itemView);

        }

        private void initAdapterView(View view) {

            txtDate = itemView.findViewById(R.id.item_txt_date);
            txtTitle = itemView.findViewById(R.id.item_txt_title);
            txtPrice = itemView.findViewById(R.id.item_txt_price);
            txtRow = itemView.findViewById(R.id.item_txt_row);
            txtEstimateTime=itemView.findViewById(R.id.item_txt_estimate_date);
            root = itemView.findViewById(R.id.root);
            txtPriceType =itemView.findViewById(R.id.txt_price_type);



            mViewContent = itemView.findViewById(R.id.view_list_main_content);
            mActionContainer = itemView.findViewById(R.id.view_list_repo_action_container);

            mActionContainer.setSelected(true);
        }

        public void bind(InfoMetaModel testModel) {
            setProductDeliveryItems(testModel, getAdapterPosition());

        }


        private void setProductDeliveryItems(InfoMetaModel info, int position) {

            if (info.getTitle().equals("633325632")) {
                root.setVisibility(View.INVISIBLE);
                mViewContent.setVisibility(View.INVISIBLE);
                mActionContainer.setVisibility(View.INVISIBLE);
               // root.setSelected(true);
            } else {

                txtEstimateTime.setText(info.getEstimateDate());
                txtRow.setText(String.valueOf(position + 1));
                txtDate.setText("تاریخ ثبت : " + info.getFarsiDate());
                txtTitle.setText(info.getTitle());
                String str = "<font color=red><b>" +
                        splitDigits(info.getPrice()) +
                        "</b></font>"
                        + "  ریال ";
                Spanned strHtml = Html.fromHtml(str);
                txtPrice.setText(strHtml);

                txtPriceType.setText(info.getPriceTypeName());


           }


        }
    }


    class ItemSwipeWithActionWidthViewHolder extends ItemBaseViewHolder implements Extension {

        View mActionViewDelete;
        View mActionViewUpdate;

        public ItemSwipeWithActionWidthViewHolder(View itemView) {
            super(itemView);
            mActionViewDelete = itemView.findViewById(R.id.view_list_repo_action_delete);
            mActionViewUpdate = itemView.findViewById(R.id.view_list_repo_action_update);
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

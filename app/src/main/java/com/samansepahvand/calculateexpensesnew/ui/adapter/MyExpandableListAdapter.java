package com.samansepahvand.calculateexpensesnew.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.db.PriceType;
import com.samansepahvand.calculateexpensesnew.infrastructure.expandableListView.PriceTypeHeader;
import com.samansepahvand.calculateexpensesnew.ui.dialog.DialogFragmentPriceType;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private final SparseArray<PriceTypeHeader> groups;
    public LayoutInflater inflater;
    public Activity activity;

    SharedPreferences preferences;


    public interface IGetPriceType {
        void GetPriceType(PriceType priceType);

    }

    private IGetPriceType _iGetPriceType;

    public MyExpandableListAdapter(Activity act, SparseArray<PriceTypeHeader> groups, IGetPriceType _iGetPriceType) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
        preferences = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE);
        this._iGetPriceType = _iGetPriceType;

    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).priceTypeList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final PriceType children = (PriceType) getChild(groupPosition, childPosition);
        TextView text = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_details, null);
        }
        text = (TextView) convertView.findViewById(R.id.textView1);
        text.setText(children.getPriceTypeItemName());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                /*  this action let  us to send data from adapter to dialog fragment

                Bundle data = new Bundle();//create bundle instance
                data.putSerializable("PriceType", children);//put string to pass with a key value
              //   /*for activity  ud.show(getSupportFragmentManager(),"tagg");
              // userPopUp.show(((FragmentActivity)activity).getSupportFragmentManager(),"tagg");
                userPopUp.setArguments(data);
                userPopUp.show(((FragmentActivity)activity).getSupportFragmentManager(),"tagg");


                 */

                _iGetPriceType.GetPriceType(children);

                savePriceType(children);

            }
        });
        return convertView;
    }


    private void savePriceType(PriceType priceType) {

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("getPriceTypeItemId", priceType.getPriceTypeItemId() + "");
        editor.putString("getPriceTypeItemName", priceType.getPriceTypeItemName());
        editor.putString("getPriceTypeId", priceType.getPriceTypeId());


        editor.apply();

        ///  dialogFragmentPriceType.dismiss();
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).priceTypeList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.listrow_group, null);
        }


        PriceTypeHeader group = (PriceTypeHeader) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(group.priceTypeName);
        ((CheckedTextView) convertView).setCompoundDrawablesWithIntrinsicBounds(0, 0, group.pic, 0);


        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}
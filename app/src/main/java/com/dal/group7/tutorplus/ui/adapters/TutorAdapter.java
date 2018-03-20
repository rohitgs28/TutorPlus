package com.dal.group7.tutorplus.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.model.TutorModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * * Adapter class for holding tutor data
 ***/
public class TutorAdapter extends BaseAdapter {
    private List<TutorModel> tutorModelList;
    private Context ctx;

public TutorAdapter(Context ctx, List<TutorModel> tutorModelList){

    this.ctx = ctx;
    this.tutorModelList = tutorModelList;
}



    @Override
    public int getCount() {
        return tutorModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return tutorModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    TutorModel tutorModel = tutorModelList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_tutor_view, null);

        }
        TextView tvSlNo = (TextView) convertView.findViewById(R.id.tv_slno);
        tvSlNo.setText(tutorModel.getName());
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
        tvName.setText(tutorModel.getAge());
        TextView tvPhone = (TextView) convertView.findViewById(R.id.tv_phone);
        tvPhone.setText(tutorModel.getPhone());

        return convertView;
    }
}

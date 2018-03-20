package com.dal.group7.tutorplus.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.model.TutorModel;

import java.util.ArrayList;
import java.util.List;

public class TutorAdapterTest extends ArrayAdapter {
    List<TutorModel> list = new ArrayList<>();

    public TutorAdapterTest(@NonNull Context context, @LayoutRes int resource,List<TutorModel> list) {
        super(context, resource,list);
    }

    public void add(TutorModel object) {
        list.add(object);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TutorHolder tutorHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_tutor_view, null);
            tutorHolder = new TutorHolder();
            tutorHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tutorHolder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            tutorHolder.tv_slno = (TextView) convertView.findViewById(R.id.tv_slno);
            convertView.setTag(tutorHolder);
        }
        else{
            tutorHolder = (TutorHolder)convertView.getTag();
        }
        TutorModel tutorModel = (TutorModel) getItem(position);
        tutorHolder.tv_name.setText(tutorModel.getName());
        tutorHolder.tv_phone.setText(tutorModel.getPhone());
        tutorHolder.tv_slno.setText(tutorModel.getAge());
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    static class TutorHolder{
        TextView tv_slno,tv_name,tv_phone;

    }
}

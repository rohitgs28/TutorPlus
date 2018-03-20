package com.dal.group7.tutorplus.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.model.pojos.tutorModel;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
	/**
	 * * Adapter class
	 ***/

	private Context mContext;
	ArrayList<tutorModel> arrayModel;
	private LayoutInflater inflater;

	public ListAdapter(Context context, ArrayList<tutorModel> arrayModel) {
		mContext = context;
		this.arrayModel = arrayModel;
		inflater= (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return arrayModel.size();
	}

	@Override
	public Object getItem(int position) {
		return arrayModel.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	return null;
	}
}
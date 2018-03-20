package com.dal.group7.tutorplus.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.model.TutorReviewModel;

import java.util.ArrayList;

import static android.R.attr.rating;
/**
 * * Adapter class for review
 ***/
public class reviewListAdapter  extends ArrayAdapter<TutorReviewModel>{
    private Context mContext;
    private int mResource;
    private int lastPosition=-1;

    private static class ViewHolder {
        TextView name1;
        TextView review;
        RatingBar rating;
    }


    public reviewListAdapter(Context context, int resource, ArrayList<TutorReviewModel> objects) {
        super(context, resource, objects);

        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String name = getItem(position).getName();
        String review = getItem(position).getReview();
        Float rating1 = getItem(position).getRating();

        final View result;
        com.dal.group7.tutorplus.ui.adapters.reviewListAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new com.dal.group7.tutorplus.ui.adapters.reviewListAdapter.ViewHolder();
            holder.name1 = (TextView) convertView.findViewById(R.id.textView1);
            holder.review = (TextView) convertView.findViewById(R.id.textView3);
            holder.rating = (RatingBar) convertView.findViewById(R.id.displayRating);

            result = convertView;
            convertView.setTag(holder);
        }
        else{
            holder = (com.dal.group7.tutorplus.ui.adapters.reviewListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.name1.setText(name);
        holder.review.setText(review);
        holder.rating.setRating(rating1);
        return convertView;
    }

}


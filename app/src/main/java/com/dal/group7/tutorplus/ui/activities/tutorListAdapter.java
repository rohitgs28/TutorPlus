package com.dal.group7.tutorplus.ui.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.model.TutorModel;
import com.google.android.gms.vision.text.Text;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

/**
 * Created by raksh on 14-11-2017.
 */



public class tutorListAdapter extends ArrayAdapter<TutorModel>
{
    private Context mContext;
    private int mResource;
    private int lastPosition=-1;




    private static class ViewHolder {
        TextView name1;
        TextView degree1;
        TextView level1;
        TextView fees1;
        ImageView img1;
        TextView storeid;
    }

    public tutorListAdapter(Context context, int resource, ArrayList<TutorModel> objects) {
        super(context, resource, objects);

        mContext = context;
        mResource = resource;
    }










    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        setupImageLoader();











        String name = getItem(position).getName();
        String degree = getItem(position).getQualification();
        String level = getItem(position).getLevel();
        String fees = getItem(position).getPriceperSession();
        String imgUrl = getItem(position).getImage();
        int id = getItem(position).getTutorId();
        String id_real = String.valueOf(id);

        final View result;
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.name1 = (TextView) convertView.findViewById(R.id.textView1);
            holder.degree1 = (TextView) convertView.findViewById(R.id.textView2);
            holder.level1 = (TextView) convertView.findViewById(R.id.textView3);
            holder.fees1 = (TextView) convertView.findViewById(R.id.textView4);
            holder.img1 = (ImageView) convertView.findViewById(R.id.iv);
            holder.storeid = (TextView) convertView.findViewById(R.id.storeid);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.name1.setText(name);
        holder.degree1.setText(degree);
        holder.level1.setText(level);
        holder.fees1.setText(fees);
        holder.storeid.setText(id_real);

        ImageLoader imageLoader = ImageLoader.getInstance();
        int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed1",null,mContext.getPackageName());

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        imageLoader.displayImage(imgUrl, holder.img1, options);

        return convertView;
    }


    private void setupImageLoader(){
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                //.cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }



}





















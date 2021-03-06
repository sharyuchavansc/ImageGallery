package com.home.photogallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    Context applicationContext;
    int sample;
    List<PhotoModel> s;

    public ImageAdapter(Context applicationContext, int sample, List<PhotoModel> s) {
        this.applicationContext = applicationContext;
        this.sample = sample;
        this.s = s;
    }

    @Override
    public int getCount() {
        return s.size();
    }

    @Override
    public Object getItem(int i) {
        return s.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater;
        if(view == null){
            layoutInflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.sample, viewGroup,false);

        }

        TextView t1;
        ImageView img;

        t1 = view.findViewById(R.id.t1);
        img = view.findViewById(R.id.img);

        t1.setText(s.get(i).getAuthor());


        ImageLoader.getInstance().displayImage(s.get(i).getFilename(),img);

        return view;
    }
}

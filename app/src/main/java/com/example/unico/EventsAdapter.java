package com.example.unico;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

public class EventsAdapter extends BaseAdapter {
       private Context mContext;

       public int[] imageArray={
               R.drawable.eventss,R.drawable.eventsss,R.drawable.eventssss,
               R.drawable.eventsssss,R.drawable.eventsssssss,
               R.drawable.eventt,R.drawable.eventtt,R.drawable.eventttt,
               R.drawable.eventtttt
       };

    public EventsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return imageArray.length;
    }

    @Override
    public Object getItem(int position) {
        return imageArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView =new ImageView(mContext);
        imageView.setImageResource(imageArray[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imageView.setLayoutParams(new GridLayout.LayoutParams(350,340));

        return imageView;
    }
}

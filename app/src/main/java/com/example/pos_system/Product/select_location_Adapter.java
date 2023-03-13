package com.example.pos_system.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pos_system.R;
import com.example.pos_system.location.LocationData;

import java.util.List;

public class select_location_Adapter extends BaseAdapter {

    List<LocationData> locationData;
    Context context;

    public select_location_Adapter(List<LocationData> locationData, Context context) {
        this.locationData = locationData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return locationData.size();
    }

    @Override
    public Object getItem(int i) {
        return locationData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.store_new_item,null,false);
        TextView textView;
        textView = view.findViewById(R.id.storetext);
        textView.setText(String.valueOf(locationData.get(i).getLocName()));

        return view;
    }
}

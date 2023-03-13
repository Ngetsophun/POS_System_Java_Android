package com.example.pos_system.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pos_system.R;
import com.example.pos_system.location.LocationData;
import com.example.pos_system.model.CatogoryData;

import java.util.List;

public class selectAdpter extends BaseAdapter {
    List<CatogoryData> catogoryData;

    Context context;

    public selectAdpter(List<CatogoryData> catogoryData, Context context) {
        this.catogoryData = catogoryData;
        this.context = context;
    }



    @Override
    public int getCount() {

        return catogoryData.size();
    }

    @Override
    public Object getItem(int i) {

        return catogoryData.get(i);
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
        textView.setText(String.valueOf(catogoryData.get(i).getCatoname()));



        return view;
    }
}

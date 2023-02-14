package com.example.pos_system.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.pos_system.AddNewUser;
import com.example.pos_system.AddNewitem;
import com.example.pos_system.Catogory.CatogoryItems;
import com.example.pos_system.HomePage;
import com.example.pos_system.R;

import java.util.List;

public class adapter_main extends BaseAdapter {
    List<HomePage> dataMenu;
    Context context;

    public adapter_main(List<HomePage> dataMenu, Context context) {
        this.dataMenu = dataMenu;
        this.context = context;

    }


    @Override
    public int getCount() {
        return dataMenu.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.custom_menu,viewGroup,false);

        }
        TextView title;
        ImageView imgView;
        title = view.findViewById(R.id.txtitlte);
        imgView = view.findViewById(R.id.ImageMenu);
        CardView cardView = view.findViewById(R.id.cardView);

        title.setText(dataMenu.get(i).getTitle());
        imgView.setImageResource(dataMenu.get(i).getImg());
        cardView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (dataMenu.get(i).getTitle()){
                case "Invoice":
                context.startActivity(new Intent(context, AddNewitem.class));break;
                case "Catogory":
                    context.startActivity(new Intent(context, CatogoryItems.class));break;
                case "User":
                context.startActivity(new Intent(context, AddNewUser.class));break;
            }
        }
    });


        return  view;
    }


}



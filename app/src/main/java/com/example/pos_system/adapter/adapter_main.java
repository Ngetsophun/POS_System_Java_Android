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

import com.example.pos_system.Card.CardActivity;
import com.example.pos_system.Customer.View_Customer;
import com.example.pos_system.Invoice.Invoiceitems;

import com.example.pos_system.HomePage;
import com.example.pos_system.Product.ViewAll_item;
import com.example.pos_system.R;
import com.example.pos_system.Catogory.View_Catogoey;
import com.example.pos_system.Sale.Sale_ProductActivity;
import com.example.pos_system.User.ViewUser;
import com.example.pos_system.location.LocationActivity;

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
                context.startActivity(new Intent(context, Invoiceitems.class));break;
                case "Catogory":
                    context.startActivity(new Intent(context, View_Catogoey.class));break;
                case "User":
                context.startActivity(new Intent(context, ViewUser.class));break;
                case "Product":
                context.startActivity(new Intent(context, ViewAll_item.class));break;
                case "Customer":
                context.startActivity(new Intent(context, View_Customer.class));break;
                case "Location":
                context.startActivity(new Intent(context, LocationActivity.class));break;
                case "Sale":
                    context.startActivity(new Intent(context,Sale_ProductActivity.class));break;
                case "Card":
                    context.startActivity(new Intent(context, CardActivity.class));break;

            }
        }
    });

        return  view;
    }



}



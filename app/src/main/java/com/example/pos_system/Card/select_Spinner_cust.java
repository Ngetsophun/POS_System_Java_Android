package com.example.pos_system.Card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pos_system.Customer.CustomerData;
import com.example.pos_system.R;

import java.util.List;

public class select_Spinner_cust extends BaseAdapter {

    List<CustomerData> customerDataList;
    Context context;

    public select_Spinner_cust(List<CustomerData> customerDataList, Context context) {
        this.customerDataList = customerDataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return customerDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return customerDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=  LayoutInflater.from(context).inflate(R.layout.store_spinner_cust,null,false);
        TextView textView;
        textView = view.findViewById(R.id.store_spinner_customer);
        textView.setText(String.valueOf(customerDataList.get(i).getName()));
        return view;
    }
}

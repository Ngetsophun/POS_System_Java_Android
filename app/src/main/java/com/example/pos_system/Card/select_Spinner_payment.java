package com.example.pos_system.Card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pos_system.Payment.PaymentData;
import com.example.pos_system.R;

import java.util.List;

public class select_Spinner_payment extends BaseAdapter {

    List<PaymentData> paymentDataList;
    Context context;

    public select_Spinner_payment(List<PaymentData> paymentDataList, Context context) {
        this.paymentDataList = paymentDataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return paymentDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return paymentDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.store_spinner_payment,null,false);
        TextView textView;
        textView = view.findViewById(R.id.store_spinner_paymnet);
        textView.setText(String.valueOf(paymentDataList.get(i).getPaymentType()));
        return view;
    }
}

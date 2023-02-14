package com.example.pos_system;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.pos_system.adapter.adapter_main;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {

    GridView gridView;
    List<HomePage>datahomepage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        datahomepage = new ArrayList<>();

        datahomepage.add(new HomePage("Invoice",R.drawable.invoice2));
        datahomepage.add(new HomePage("Items",R.drawable.itemimg));
        datahomepage.add(new HomePage("Report",R.drawable.report));
        datahomepage.add(new HomePage("Catogory",R.drawable.person));
        datahomepage.add(new HomePage("User",R.drawable.userimg));
        datahomepage.add(new HomePage("About",R.drawable.about_us1));
        gridView = view.findViewById(R.id.gridview);



        gridView.setAdapter(new adapter_main(datahomepage,requireContext()));

        return view;
    }
}
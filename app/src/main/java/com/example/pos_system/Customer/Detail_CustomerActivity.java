package com.example.pos_system.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import com.example.pos_system.R;
import com.example.pos_system.dao.UserDAO;
import com.example.pos_system.database.UserDatabase;
import com.example.pos_system.databinding.ActivityViewCustomerBinding;

import java.util.List;

public class Detail_CustomerActivity extends AppCompatActivity {

    ActivityViewCustomerBinding binding;


    public Detail_CustomerActivity(List<CustomerData> customerData) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityViewCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());






    }
}
package com.example.pos_system.Card;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pos_system.Customer.CustomerData;
import com.example.pos_system.Payment.PaymentData;
import com.example.pos_system.Product.selectAdpter;
import com.example.pos_system.R;
import com.example.pos_system.dao.UserDAO;
import com.example.pos_system.database.UserDatabase;
import com.example.pos_system.databinding.ActivityCardBinding;

import java.util.List;

public class CardActivity extends AppCompatActivity {

    ActivityCardBinding binding;
    UserDatabase userDatabase;
    UserDAO userDAO;

    List<CustomerData> customerDataList;
    List<PaymentData> paymentDataList;
    RecyclerView recyclerView;

    String Total;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        recyclerView = findViewById(R.id.recycler_CardList);



        userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        userDAO = userDatabase.userDAO();



        List<CardData> cardData = userDAO.getAllCard();
        Card_Adapter cardAdapter = new Card_Adapter(cardData,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cardAdapter);


        Spinner spinnercustomer = findViewById(R.id.Spinner_ShowCato);
        Spinner spinnerpayment  =  findViewById(R.id.Spinner_showPaymet);


        userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();
        customerDataList = userDAO.getAllCustomer();
        select_Spinner_cust adptercust = new select_Spinner_cust(customerDataList,getApplicationContext());
        spinnercustomer.setAdapter(adptercust);


        paymentDataList = userDAO.getAllPayment();
        select_Spinner_payment adaperpaymnet = new select_Spinner_payment(paymentDataList,getApplicationContext());
        spinnerpayment.setAdapter(adaperpaymnet);

       // binding.SubTotal.setText();

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });







    }




}
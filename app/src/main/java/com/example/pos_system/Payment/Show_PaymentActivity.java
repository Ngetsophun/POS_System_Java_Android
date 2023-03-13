package com.example.pos_system.Payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pos_system.R;
import com.example.pos_system.dao.UserDAO;
import com.example.pos_system.database.UserDatabase;
import com.example.pos_system.databinding.ActivityShowPaymentBinding;

import java.util.List;

public class Show_PaymentActivity extends AppCompatActivity {

    ActivityShowPaymentBinding binding;
    UserDatabase userDatabase;
    UserDAO userDAO;
    RecyclerView recyclerView;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        recyclerView = findViewById(R.id.show_allPayment);
        userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        userDAO = userDatabase.userDAO();

        List<PaymentData> paymentData = userDAO.getAllPayment();
        AdapterPayment paymentAdapter = new AdapterPayment(paymentData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(paymentAdapter);



        //add new Payment
       binding.btnAddPayment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String payment = binding.addPaymentType.getText().toString();
               String description  = binding.addPaymentDsc.getText().toString();

               userDatabase.userDAO().InsertPayment(new PaymentData(id,payment,description));
               userDAO = userDatabase.userDAO();

               Toast.makeText(Show_PaymentActivity.this, "Add Success", Toast.LENGTH_SHORT).show();

               binding.addPaymentType.setText("");
               binding.addPaymentDsc.setText("");

           }
       });



        binding.floatpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.addpaymentActivity.setVisibility(View.VISIBLE);
                binding.showpaymentActivity.setVisibility(View.GONE);

            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.addpaymentActivity.setVisibility(View.GONE);
                binding.showpaymentActivity.setVisibility(View.VISIBLE);
            }
        });


    }

    //Adapter
    public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.MyviewHolder>{

        List<PaymentData> paymentData;

        public AdapterPayment(List<PaymentData> paymentData) {
            this.paymentData = paymentData;
        }

        @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_showpayment,parent,false);
            return new MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
            holder.textpayment.setText(String.valueOf(paymentData.get(position).getPaymentType()));
            holder.textdesc.setText(String.valueOf(paymentData.get(position).getDecription()));

            //delete
            holder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();

                    userDAO = userDatabase.userDAO();
                    userDAO.DeletePaymentByid(paymentData.get(position).getPayId());
                    paymentData.remove(position);
                    notifyDataSetChanged();

                }
            });

            final PaymentData paymentData1 = paymentData.get(position);
            if(paymentData1 == null){
                return;
            }

//          //Update
//            holder.img_update.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(view.getContext(), Update_PaymentActivity.class);
//                    intent.putExtra("payment",paymentData1);
//                    view.getContext().startActivity(intent);
//
//                    Toast.makeText(Show_PaymentActivity.this, "Update", Toast.LENGTH_SHORT).show();
//                }
//            });

            holder.cardpayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Update_PaymentActivity.class);
                    intent.putExtra("payment",paymentData1);
                    view.getContext().startActivity(intent);

                    Toast.makeText(Show_PaymentActivity.this, "Update", Toast.LENGTH_SHORT).show();

                }
            });
        }
        @Override
        public int getItemCount() {
            return paymentData.size();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder{

            TextView textpayment,textdesc;
            ImageView img_delete;
            CardView cardpayment;
            public MyviewHolder(@NonNull View itemView) {
                super(itemView);
                textpayment = itemView.findViewById(R.id.pay_payment);
                textdesc = itemView.findViewById(R.id.pay_Description);

                img_delete = itemView.findViewById(R.id.img_pay_delete);
                cardpayment = itemView.findViewById(R.id.cardshowPayment);


            }
        }
    }


}


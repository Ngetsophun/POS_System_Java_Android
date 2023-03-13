package com.example.pos_system.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pos_system.Catogory.View_Catogoey;
import com.example.pos_system.CurrentDateHelper;
import com.example.pos_system.R;
import com.example.pos_system.dao.UserDAO;
import com.example.pos_system.database.UserDatabase;
import com.example.pos_system.databinding.ActivityViewCustomerBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class View_Customer extends AppCompatActivity {

    ActivityViewCustomerBinding binding;
    UserDatabase userDatabase;
    RecyclerView recyclerView;
    UserDAO userDAO;
    int id;
   String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityViewCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.show_customer);


        userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        userDAO = userDatabase.userDAO();

        List<CustomerData> customerData = userDAO.getAllCustomer();

        AdapterCustomer adapter = new AdapterCustomer(customerData);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        binding.floatCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               binding.AddCustomActivity.setVisibility(View.VISIBLE);
               binding.ShowCustomActivity.setVisibility(View.GONE);
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.AddCustomActivity.setVisibility(View.GONE);
                binding.ShowCustomActivity.setVisibility(View.VISIBLE);
            }
        });


       // Select Choose date
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
       binding.addCustDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(View_Customer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month+1;
                        date = year+"/"+month+"/"+day;
                       binding.addCustDob.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });



        //Add Customer
        binding.btnaddcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String date_save = simpleDateFormat.format(date);


                String name = binding.addCustName.getText().toString().trim();
                String phone = binding.addCustPhone.getText().toString().trim();
                String email = binding.addCustEmail.getText().toString();
                String address = binding.addCustAddress.getText().toString();
                String dob = binding.addCustDob.getText().toString();



                String gender = "";
                if(binding.addCustMale.isChecked()){
                    gender = "Male";
                }
                if(binding.addCustFemale.isChecked()){
                    gender  = "Female";
                }

                userDatabase.userDAO().InsertCustomer(new CustomerData(id,name,phone,gender,email,address,dob,date_save));
                userDAO = userDatabase.userDAO();

                Toast.makeText(View_Customer.this, "Add Success", Toast.LENGTH_SHORT).show();

                binding.addCustName.setText("");
                binding.addCustPhone.setText("");
                binding.addCustEmail.setText("");
                binding.addCustAddress.setText("");
                binding.addCustDob.setText("");

            }
        });

    }



    //Adapter Customer
    public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.MyviewHolder>{
        List<CustomerData> customerData;

        public AdapterCustomer(List<CustomerData> customerData) {
            this.customerData = customerData;
        }

        @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view_customer,parent,false);
            return new MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

           // show
            holder.name.setText(String.valueOf(customerData.get(position).getName()));
            holder.phone.setText(String.valueOf(customerData.get(position).getPhone()));
            holder.dateNow.setText(CurrentDateHelper.getCurrentDate());






            //delete
            holder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries().build();

                    userDAO = userDatabase.userDAO();
                    userDAO.DeleteCustomerByid(customerData.get(position).getId());
                    customerData.remove(position);
                    notifyDataSetChanged();
                }
            });


            //update
            final CustomerData customerData1 = customerData.get(position);
            if(customerData1 == null){
                return;
            }

            holder.cartcustom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   Intent intent = new Intent(view.getContext(),Update_CustomerActivity.class);
                    intent.putExtra("customer",customerData1);

                    view.getContext().startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return customerData.size();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder{
            TextView name,phone,dateNow;

            ImageView img_delete;

            CardView cartcustom;
            public MyviewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.cust_names);
                phone = itemView.findViewById(R.id.cust_phones);

                dateNow = itemView.findViewById(R.id.cust_datenow);
                img_delete = itemView.findViewById(R.id.img_Cust_deletes);
                cartcustom = itemView.findViewById(R.id.cardCustomer);





            }
        }

    }

}

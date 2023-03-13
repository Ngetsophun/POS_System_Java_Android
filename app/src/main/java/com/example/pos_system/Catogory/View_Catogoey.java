package com.example.pos_system.Catogory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pos_system.Payment.Update_PaymentActivity;
import com.example.pos_system.R;
import com.example.pos_system.dao.UserDAO;
import com.example.pos_system.database.UserDatabase;
import com.example.pos_system.databinding.ActivityViewCatogoeyBinding;
import com.example.pos_system.model.CatogoryData;

import java.util.List;

public class View_Catogoey extends AppCompatActivity {


    ActivityViewCatogoeyBinding binding;
    UserDatabase database;
    UserDAO userDAO;

    RecyclerView recyclerView;

    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewCatogoeyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.showCatogoryitem);

        database = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        userDAO = database.userDAO();
//
      List<CatogoryData> catogoryData = userDAO.getAllCato();
        AdapterCatogory adapter= new AdapterCatogory(catogoryData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);




        //Add Catogoty
        binding.btnAddCatogory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.catoAddName.getText().toString();
                String name1 = binding.CatoAddName1.getText().toString();

                database.userDAO().InsertCotogory(new CatogoryData(id,name,name1));
                userDAO = database.userDAO();

                Toast.makeText(View_Catogoey.this, "Add Sucess", Toast.LENGTH_SHORT).show();

                binding.catoAddName.setText("");
                binding.CatoAddName1.setText("");


            }

        });




        binding.floatCatogory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               binding.AddCatoActivity.setVisibility(View.VISIBLE);
               binding.showCatoActivity.setVisibility(View.GONE);
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.AddCatoActivity.setVisibility(View.GONE);
                binding.showCatoActivity.setVisibility(View.VISIBLE);
            }
        });

    }



    //Adapter Catogory
    public class AdapterCatogory extends RecyclerView.Adapter<AdapterCatogory.MyviewHolder>{

        List<CatogoryData> catogoryData;

        public AdapterCatogory(List<CatogoryData> catogoryData) {
            this.catogoryData = catogoryData;
        }

        @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_catogory,parent,false);
            return new MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
            holder.txtname.setText(String.valueOf(catogoryData.get(position).getCatoname()));
            holder.txtname1.setText(String.valueOf(catogoryData.get(position).getCatoname1()));

            //delete
            holder.imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    database = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                    userDAO = database.userDAO();
                    userDAO.DeleteCatoByid(catogoryData.get(position).getId());
                    catogoryData.remove(position);
                    notifyDataSetChanged();
                }
            });

            final CatogoryData catogoryData1 = catogoryData.get(position);
                if(catogoryData1 == null){
                    return;
                }

             holder.cardcato.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     Intent intent = new Intent(view.getContext(), Update_Catogory.class);
                     intent.putExtra("catogory",catogoryData1);
                     view.getContext().startActivity(intent);

                     Toast.makeText(View_Catogoey.this, "Update Success", Toast.LENGTH_SHORT).show();
                 }
             });

        }

        @Override
        public int getItemCount() {
            return catogoryData.size();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder{

            TextView txtname,txtname1;
            ImageView imgdelete;
            CardView cardcato;

            public MyviewHolder(@NonNull View itemView) {
                super(itemView);
                txtname = itemView.findViewById(R.id.namcato);
                txtname1 = itemView.findViewById(R.id.name1cato);
                imgdelete = itemView.findViewById(R.id.imgdelete);

                cardcato = itemView.findViewById(R.id.cardCatogoryitem);
            }
        }
    }


}
package com.example.pos_system.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pos_system.Payment.Show_PaymentActivity;
import com.example.pos_system.Payment.Update_PaymentActivity;
import com.example.pos_system.R;
import com.example.pos_system.dao.UserDAO;
import com.example.pos_system.database.UserDatabase;
import com.example.pos_system.databinding.ActivityLocationBinding;

import java.util.List;

public class LocationActivity extends AppCompatActivity {

    ActivityLocationBinding binding;

    UserDatabase userDatabase;
    UserDAO userDAO;

    RecyclerView recyclerView;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.showLocationitem);
        userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                        .allowMainThreadQueries()
                                .fallbackToDestructiveMigration().build();

        userDAO = userDatabase.userDAO();
        List<LocationData> locationData = userDAO.getAllLocat();
        AdapterLocation adapterLocation = new AdapterLocation(locationData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterLocation);


        //add location
        binding.btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.locAddName.getText().toString();
                String nameKH = binding.locAddName1.getText().toString();

                userDatabase.userDAO().InsertLocation(new LocationData(id,name,nameKH));
                userDAO = userDatabase.userDAO();
                Toast.makeText(LocationActivity.this, "Save Success", Toast.LENGTH_SHORT).show();

                binding.locAddName.setText("");
                binding.locAddName1.setText("");

            }
        });

        binding.floatlocat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               binding.btnAddLocation.setVisibility(View.VISIBLE);
               binding.showLocationActivity.setVisibility(View.GONE);

            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnAddLocation.setVisibility(View.GONE);
                binding.showLocationActivity.setVisibility(View.VISIBLE);
            }
        });


    }

    //Adapter Location
    public class AdapterLocation extends RecyclerView.Adapter<AdapterLocation.MyviewHolder>{

        List<LocationData> locationData;

        public AdapterLocation(List<LocationData> locationData) {
            this.locationData = locationData;
        }

        @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_show_location,parent,false);
            return new MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
            holder.name.setText(String.valueOf(locationData.get(position).getLocName()));
            holder.name1.setText(String.valueOf(locationData.get(position).getLocName1()));

            holder.imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries().build();
                    userDAO = userDatabase.userDAO();
                    userDatabase.userDAO().DeleteLocationById(locationData.get(position).getLocID());
                    locationData.remove(position);
                    notifyDataSetChanged();
                }
            });

            //Update location
            final LocationData locationData1 = locationData.get(position);
            if(locationData1 == null){
                return;
            }

            holder.cardloc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),Update_Location_Activity.class);

                    intent.putExtra("location",locationData1);

                    view.getContext().startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                    Toast.makeText(LocationActivity.this, "Update", Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public int getItemCount() {
            return locationData.size();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder{

            TextView name,name1;
            ImageView imgupdate,imgdelete;

            CardView cardloc;
            public MyviewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.nameloc);
                name1 = itemView.findViewById(R.id.name1loc);
//                imgupdate = itemView.findViewById(R.id.img_loc_update);
                imgdelete = itemView.findViewById(R.id.img_loc_delete);
                cardloc = itemView.findViewById(R.id.cardLocation);
            }
        }
    }
}
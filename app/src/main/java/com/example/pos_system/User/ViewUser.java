package com.example.pos_system.User;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pos_system.CurrentDateHelper;
import com.example.pos_system.Customer.View_Customer;
import com.example.pos_system.R;
import com.example.pos_system.dao.UserDAO;
import com.example.pos_system.database.UserDatabase;
import com.example.pos_system.databinding.ActivityViewUserBinding;
import com.github.drjacky.imagepicker.ImagePicker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewUser extends AppCompatActivity {
    ActivityViewUserBinding binding;
    UserDAO userDAO;
    UserDatabase userDatabase;
    RecyclerView recyclerView;
    String date = "";

    Boolean Admin = false;
    Boolean Cashier = false;

    Boolean Allow = false;
    Boolean Views = false;


    File file;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewUserBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

        recyclerView =findViewById(R.id.showUser);

            userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

            userDAO = userDatabase.userDAO();
        List<UserData> userData = userDAO.getAllUser();
        AdapterUser adapter = new AdapterUser(userData,getApplicationContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        binding.addUserAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.addUserAdmin.isChecked()){
                    binding.addUserManager.setEnabled(false);
                    binding.addUserCashier.setEnabled(false);
                }
                else {
                    binding.addUserManager.setEnabled(true);
                    binding.addUserCashier.setEnabled(true);
                }

            }
        });
        binding.addUserManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.addUserManager.isChecked()){
                    binding.addUserAdmin.setEnabled(false);
                    binding.addUserCashier.setEnabled(false);
                }else{
                    binding.addUserAdmin.setEnabled(true);
                    binding.addUserCashier.setEnabled(true);
                }
            }
        });
        binding.addUserCashier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.addUserCashier.isChecked()){
                    binding.addUserManager.setEnabled(false);
                    binding.addUserAdmin.setEnabled(false);

                    binding.addCheckDelete.setEnabled(false);
                    binding.addCheckUpdate.setEnabled(false);
                    binding.addCheckInsert.setEnabled(false);
                    binding.addCheckView.setEnabled(false);
                    binding.addCheckAllow.setEnabled(false);

                }else {
                    binding.addUserManager.setEnabled(true);
                    binding.addUserAdmin.setEnabled(true);

                    binding.addCheckDelete.setEnabled(true);
                    binding.addCheckUpdate.setEnabled(true);
                    binding.addCheckInsert.setEnabled(true);
                    binding.addCheckView.setEnabled(true);
                    binding.addCheckAllow.setEnabled(true);
                }
            }
        });



        binding.btnfloatuser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   binding.AddUserActivity.setVisibility(View.VISIBLE);
                   binding.ShowUserActivity.setVisibility(View.GONE);
                }
            });
            binding.imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.AddUserActivity.setVisibility(View.GONE);
                    binding.ShowUserActivity.setVisibility(View.VISIBLE);
                }
            });

            //select choose date
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        binding.addUserDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ViewUser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        binding.addUserDob.setText(date);

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        //Add user
        binding.btnaddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String date_save = simpleDateFormat.format(date);
                
                String name = binding.addUsername.getText().toString().trim();
                String pass = binding.addUserPassword.getText().toString().trim();
                String address = binding.addUserAddress.getText().toString();
                String dob = binding.addUserDob.getText().toString().trim();



                String role = "";
                if(binding.addUserAdmin.isChecked()){
                    role = "Admin";

                }
                if(binding.addUserManager.isChecked()){
                    role = "Manager";
                }if(binding.addUserCashier.isChecked()){
                    role = "Cashier";
                }
//

                String gender = "";
                if(binding.addUserMale.isChecked()){
                    gender = "Male";
                }
                if(binding.addUserFemale.isChecked()){
                    gender  = "Female";
                }

                
                String permission = "";
                if(binding.addCheckView.isChecked()){
                    permission = "View";
                    
                }





                userDatabase.userDAO().InsertUser(new UserData(name,pass,gender,address,role,dob,permission,file.getPath(),date_save));
                userDAO = userDatabase.userDAO();

                Toast.makeText(ViewUser.this, "Save Success", Toast.LENGTH_SHORT).show();

                binding.addUsername.setText("");
                binding.addUserPassword.setText("");
                binding.addUserAddress.setText("");
                binding.addUserDob.setText("");

            }
        });

        binding.chooseimgAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseImageAdd();
            }
        });


    }

    private  void ChooseImageAdd(){
        launcher.launch(
                ImagePicker.Companion.with(this)
                        .maxResultSize(1080,1080, true)
                        .crop()
                        .galleryOnly()
                        .createIntent()
        );
    }
    ActivityResultLauncher<Intent> launcher=
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                if(result.getResultCode()==RESULT_OK){

                    assert result.getData() != null;

                    Uri uri=result.getData().getData();

                    file = new File(uri.getPath());

                    binding.chooseimgAddUser.setImageURI(uri);
                }else if(result.getResultCode()== ImagePicker.RESULT_ERROR){
                    Toast.makeText(this, "No image pick", Toast.LENGTH_SHORT).show();
                }
            });





    //Adapter User
    public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyViewHolder>{

        List<UserData> userData;
        Context context;

        public AdapterUser(List<UserData> userData, Context context) {
            this.userData = userData;
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_user,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            Glide.with(context).load(userData.get(position).getImage()).into(holder.showImageAddUser);

            holder.name.setText(String.valueOf(userData.get(position).getName()));
            holder.role.setText(String.valueOf(userData.get(position).getRole()));
            holder.address.setText(String.valueOf(userData.get(position).getAddress()));
            holder.date.setText(CurrentDateHelper.getCurrentDate());



//            delete
            holder.imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration().build();

                    userDAO = userDatabase.userDAO();
                    userDAO.DeleteUserByid(userData.get(position).getId());
                    userData.remove(position);
                    notifyDataSetChanged();
                }
            });

            //Update
            final UserData userData1 = userData.get(position);
            if(userData1 == null){
                return;
            }

            holder.cardupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),UpdateUser.class);
                    intent.putExtra("user",userData1);
                    view.getContext().startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return userData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView name,role,address,date;
            ImageView imgdelete,showImageAddUser;

            CardView cardupdate;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.show_user_name);
                role = itemView.findViewById(R.id.Show_user_role);
                address = itemView.findViewById(R.id.Show_user_address);
                date = itemView.findViewById(R.id.Show_user_date);
                showImageAddUser = itemView.findViewById(R.id.show_img_user);


                imgdelete = itemView.findViewById(R.id.img_deleteUser);
                cardupdate = itemView.findViewById(R.id.cardShowUser);
            }
        }
    }

}
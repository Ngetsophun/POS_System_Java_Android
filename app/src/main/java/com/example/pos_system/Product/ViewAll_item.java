package com.example.pos_system.Product;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pos_system.Catogory.Update_Catogory;
import com.example.pos_system.Catogory.View_Catogoey;
import com.example.pos_system.R;
import com.example.pos_system.Sale.Sale_ProductActivity;
import com.example.pos_system.dao.UserDAO;
import com.example.pos_system.database.UserDatabase;
import com.example.pos_system.databinding.ActivityViewAllItemBinding;
import com.example.pos_system.location.LocationData;
import com.example.pos_system.model.CatogoryData;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.appbar.AppBarLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewAll_item extends AppCompatActivity {

    ActivityViewAllItemBinding binding;
    RecyclerView recyclerView;
    UserDatabase userDatabase;
    UserDAO userDAO;

    int id;
    String catoID;
    String locID;

    File file;

    List<LocationData> locationData;
    List<CatogoryData> catogoryData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewAllItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        recyclerView = findViewById(R.id.recycler_Pro_View);

        userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        userDAO = userDatabase.userDAO();


        List<ProductData> productData = userDAO.getAllProduct();
        AdapterProduct adapter = new AdapterProduct(productData, getApplicationContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        Spinner spinner  = findViewById(R.id.Spinner_select_Cato);
        Spinner spinner1 = findViewById(R.id.Spinner_Add_location);

        userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();

         catogoryData = userDAO.getAllCato();
        selectAdpter adpter = new selectAdpter(catogoryData,getApplicationContext());
        spinner.setAdapter(adpter);


        locationData = userDAO.getAllLocat();
        select_location_Adapter adpter1 = new select_location_Adapter(locationData,getApplicationContext());
        spinner1.setAdapter(adpter1);


        //tool bar
////
//        Toolbar toolbar = findViewById(R.id.app_bar_product);
//        setSupportActionBar(toolbar);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//-------------------------------



        //Add New Product
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                catoID = catogoryData.get(i).getCatoname();

                binding.btnaddProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Date date = Calendar.getInstance().getTime();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        String date_save = simpleDateFormat.format(date);



                        String Productcode = binding.ProductBarcode.getText().toString();
                        String Productname = binding.ProductName.getText().toString();
                        String ProductnameKH = binding.ProductNameKH.getText().toString();
                        int ProductQty =Integer.parseInt(binding.ProductQty.getText().toString());
                        double ProductCost = Double.parseDouble(binding.ProductCost.getText().toString());
                        double ProductPrice = Double.parseDouble(binding.ProductPrice.getText().toString());
                        double ProductTax = Double.parseDouble(binding.ProductTax.getText().toString());


                        userDatabase.userDAO().InsertProduct(new ProductData(catoID,locID,Productcode,Productname,ProductnameKH,ProductQty,ProductCost,ProductPrice,ProductTax,file.getPath(),date_save));
                        userDAO = userDatabase.userDAO();

                        Toast.makeText(ViewAll_item.this, "Save Success", Toast.LENGTH_SHORT).show();

                        binding.ProductBarcode.setText("");
                        binding.ProductName.setText("");
                        binding.ProductNameKH.setText("");
                        binding.ProductQty.setText("");
                        binding.ProductCost.setText("");
                        binding.ProductPrice.setText("");
                        binding.ProductTax.setText("");


                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                locID = locationData.get(i).getLocName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.btnScanBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scanCode();
            }
        });


        //Add Image
        binding.chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChooseImage();
            }
        });



        binding.floatingViewPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.ActivityShowProduct.setVisibility(View.GONE);
                binding.ActivityAddProduct.setVisibility(View.VISIBLE);
            }
        });
        binding.imgBackPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.ActivityShowProduct.setVisibility(View.VISIBLE);
                binding.ActivityAddProduct.setVisibility(View.GONE);
            }
        });


        binding.imgSearchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.BarSearch.setVisibility(View.GONE);
                binding.BarCloseSearch.setVisibility(View.VISIBLE);
            }
        });
        binding.imgCloseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.BarSearch.setVisibility(View.VISIBLE);
                binding.BarCloseSearch.setVisibility(View.VISIBLE);
            }
        });



        ///search
        binding.edsearchlistProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
    private void ChooseImage(){
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

                    binding.chooseImage.setImageURI(uri);
                }else if(result.getResultCode()== ImagePicker.RESULT_ERROR){
                    Toast.makeText(this, "No image pick", Toast.LENGTH_SHORT).show();
                }
            });




    private void scanCode(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setPrompt("Scan code");
        intentIntegrator.initiateScan();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode, data);
        if(intentResult != null){
            if(intentResult.getContents() == null){
                Toast.makeText(this, "No code", Toast.LENGTH_SHORT).show();
            }else{
                binding.ProductBarcode.setText(intentResult.getContents());
                Toast.makeText(this, "Code = " + intentResult.getContents(), Toast.LENGTH_SHORT).show();

            }
        }



    }


//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.product_search,menu);
//        MenuItem item = menu.findItem(R.id.search);
//
//        SearchView searchView1 = (SearchView) item.getActionView();
//        searchView1.setQueryHint("Search here");
//
//        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                return false;
//            }
//        });
//
//
//
//
//
//        return super.onCreateOptionsMenu(menu);
//    }
}
package com.example.pos_system.Product;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomMasterTable;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pos_system.CurrentDateHelper;
import com.example.pos_system.Customer.Update_CustomerActivity;
import com.example.pos_system.Customer.View_Customer;
import com.example.pos_system.R;
import com.example.pos_system.dao.UserDAO;
import com.example.pos_system.database.UserDatabase;
import com.example.pos_system.databinding.ActivityUpdateProductBinding;
import com.example.pos_system.location.LocationData;
import com.example.pos_system.model.CatogoryData;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UpdateProduct extends AppCompatActivity {

   ActivityUpdateProductBinding binding;

    UserDatabase userDatabase;

    Boolean IS_UPDATE = false;

    ProductData productData;
    UserDAO userDAO;
    File file;
    int id;
    String loc_name;

    String cato_name;
    Boolean is_upload = false;
    Uri uri;
    String imagePath;

    List<LocationData> locationDataList;
    List<CatogoryData>catogoryDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUpdateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntenData();

        //show data for select catogory
        userDAO =userDatabase.getUserDatabase(getApplicationContext()).userDAO();
        catogoryDataList = userDAO.getAllCato();
        selectAdpter adpter = new selectAdpter(catogoryDataList,getApplicationContext());
        binding.SpinnerUpProCato.setAdapter(adpter);



        //show data for select location
        locationDataList = userDAO.getAllLocat();
        select_location_Adapter select_location_adapter = new select_location_Adapter(locationDataList,getApplicationContext());
        binding.SpinnerUpProLocation.setAdapter(select_location_adapter);



        binding.SpinnerUpProCato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cato_name = catogoryDataList.get(i).getCatoname();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.SpinnerUpProLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loc_name = locationDataList.get(i).getLocName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.btnUpProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IS_UPDATE == false){
                    UpSaveProduct saveProduct = new UpSaveProduct();
                    saveProduct.execute();
                }else {
                    UpDateItemProduct upDateItemProduct = new UpDateItemProduct();
                    upDateItemProduct.execute();

                }
            }
        });


        binding.chooseUpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                is_upload = true;
                ChooseImage();

            }
        });

        binding.btnUpScanBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scancode();;
            }
        });

    }
    private void getIntenData(){
        if(getIntent().hasExtra("product")){
            IS_UPDATE = true;
            binding.btnUpProduct.setText("UPDATE");
            productData = (ProductData) getIntent().getSerializableExtra("product");

            id = productData.getProID();
            binding.UpProBarcode.setText(String.valueOf(productData.getProBarcode()));
            binding.UpProName.setText(String.valueOf(productData.getProname()));
            binding.UpProNameKH.setText(String.valueOf(productData.getPronameKh()));

            binding.UpProQtys.setText(String.valueOf(productData.getProqty()));
            binding.UpProCost.setText(String.valueOf(productData.getProcost()));
            binding.UpProPrice.setText(String.valueOf(productData.getProprice()));
            binding.UpProTax.setText(String.valueOf(productData.getProtax()));


            Glide.with(this).load(productData.getImage()).into(binding.chooseUpImage);

            imagePath = productData.getImage();


        }else {
            IS_UPDATE = false;
            binding.btnUpProduct.setText("SAVE");
        }


    }

    class UpSaveProduct extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {


//
            ProductData productData1 = new ProductData();
            productData1.setCatoID(cato_name);
            productData1.setLocID(loc_name);

//
            productData1.setProBarcode(binding.UpProBarcode.getText().toString());
            productData1.setProname(binding.UpProName.getText().toString());
            productData1.setPronameKh(binding.UpProNameKH.getText().toString());

            productData1.setProqty(Integer.parseInt(binding.UpProQtys.getText().toString()));
            productData1.setProcost(Double.parseDouble(binding.UpProCost.getText().toString()));
            productData1.setProprice(Double.parseDouble(binding.UpProPrice.getText().toString()));
            productData1.setProtax(Double.parseDouble(binding.UpProTax.getText().toString()));
//           productData1.setImage(imagePath);

            productData1.setImage(file.getPath());
            productData1.setDate(CurrentDateHelper.getCurrentDate());

            userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();
            userDAO.InsertProduct(productData1);


            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

//            startActivity(new Intent(getApplicationContext(),ViewAll_item.class));
            Toast.makeText(UpdateProduct.this, "Save Update", Toast.LENGTH_SHORT).show();


        }
    }


    class UpDateItemProduct extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {



            double tax = Double.parseDouble(binding.UpProTax.getText().toString());

            ProductData productData = new ProductData();


            if(is_upload == true){

                productData.setCatoID(cato_name);
                productData.setLocID(loc_name);
                productData.setProID(id);

                productData.setProname(binding.UpProName.getText().toString());
                productData.setPronameKh(binding.UpProNameKH.getText().toString());
                productData.setProqty(Integer.parseInt(binding.UpProQtys.getText().toString()));
                productData.setProBarcode(binding.UpProBarcode.getText().toString());
                productData.setProprice(Double.parseDouble(binding.UpProPrice.getText().toString()));
                productData.setProcost(Double.parseDouble(binding.UpProCost.getText().toString()));
                productData.setProtax(tax);
                productData.setDate(CurrentDateHelper.getCurrentDate());
//
//                productData.setImage(imagePath);
                productData.setImage(file.getPath());

                userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();
                userDAO.UpdateProduct(productData);
            }else{
                is_upload = false;
                productData.setCatoID(cato_name);
                productData.setLocID(loc_name);
                productData.setProID(id);

                productData.setProname(binding.UpProName.getText().toString());
                productData.setPronameKh(binding.UpProNameKH.getText().toString());
                productData.setProqty(Integer.parseInt(binding.UpProQtys.getText().toString()));
                productData.setProBarcode(binding.UpProBarcode.getText().toString());
                productData.setProprice(Double.parseDouble(binding.UpProPrice.getText().toString()));
                productData.setProcost(Double.parseDouble(binding.UpProCost.getText().toString()));
                productData.setProtax(tax);
                productData.setDate(CurrentDateHelper.getCurrentDate());
//                productData.setImage(imagePath);

                productData.setImage(file.getPath());
                userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();
                userDAO.UpdateProduct(productData);

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            startActivity(new Intent(getApplicationContext(), ViewAll_item.class));
            Toast.makeText(UpdateProduct.this, "Record Update", Toast.LENGTH_SHORT).show();

        }
    }



    private  void Scancode(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setPrompt("Scan code");
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null){
            if(intentResult.getContents() == null){
                Toast.makeText(this, "No code", Toast.LENGTH_SHORT).show();
            }else {
               // binding.upBarcodePro.setText(intentResult.getContents());
                Toast.makeText(this, "Code = " + intentResult.getContents(), Toast.LENGTH_SHORT).show();
            }
        }
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

                    uri=result.getData().getData();

                    file = new File(uri.getPath());

                    binding.chooseUpImage.setImageURI(uri);
                }else if(result.getResultCode()== ImagePicker.RESULT_ERROR){
                    Toast.makeText(this, "No image pick", Toast.LENGTH_SHORT).show();
                }
            });
}
package com.example.pos_system.dao;

import android.icu.util.LocaleData;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pos_system.Card.CardData;
import com.example.pos_system.Customer.CustomerData;
import com.example.pos_system.Payment.PaymentData;
import com.example.pos_system.Product.ProductData;
import com.example.pos_system.Sale.SaleData;
import com.example.pos_system.User.UserData;
import com.example.pos_system.location.LocationData;
import com.example.pos_system.model.CatogoryData;

import java.util.List;

@Dao
public interface UserDAO {



    @Insert
    void InsertLocation(LocationData locationData);
    @Insert
    void InsertUser(UserData userData);

    @Insert
    void InsertProduct(ProductData productData);

    @Insert
    void InsertCustomer(CustomerData customerData);

    @Insert
    void InsertPayment(PaymentData paymentData);
    @Insert
    void InsertCotogory(CatogoryData catogoryData);

    @Insert
    void InsertCard(CardData cardData);




   @Query("SELECT * FROM catogory")
    List<CatogoryData> getAllCato();


  @Query("SELECT * FROM tbUser")
   List<UserData> getAllUser();

    @Query("SELECT * FROM product")
    List<ProductData> getAllProduct();

    @Query("SELECT * FROM customer")
    List<CustomerData> getAllCustomer();


    @Query("SELECT * FROM payment")
    List<PaymentData> getAllPayment();

    @Query("SELECT * FROM Location")
    List<LocationData> getAllLocat();

    @Query("SELECT * FROM sale")
    List<SaleData >getAllSale();

    @Query("SELECT * FROM card")
    List<CardData> getAllCard();


//
//   @Query("UPDATE product SET CatoID =:CatoID, ProBarcode =:Probarcode, Proname =:Proname, PronameKh=:PronameKH,Proprice =:ProPrice WHERE ProID =:ProID")
//   void UpdateProductByid(String CatoID,String Probarcode, String ProID,String Proname,String PronameKH,Double ProPrice);

    @Update
    void UpdateProduct(ProductData productData);
     @Update
     void UpdatePayment(PaymentData paymentData);
     @Update
     void UpdateCatogory(CatogoryData catogoryData);

     @Update
     void UpdateCustomer(CustomerData customerData);

     @Update
     void UpdaAllteUser(UserData userData);

     @Update
     void UpdateAllLocatioon(LocationData locationData);








   @Query("DELETE FROM catogory WHERE id =:id")
    void DeleteCatoByid(int id);

   @Query("DELETE FROM tbUser WHERE id =:id")
    void DeleteUserByid(int id);

   @Query("DELETE FROM product WHERE ProID =:ProID")
   void DeleteProductByid(int ProID);

   @Query("DELETE FROM customer WHERE id =:Cusid")
   void DeleteCustomerByid(int Cusid);

   @Query("DELETE FROM payment WHERE payId =:Payid")
   void DeletePaymentByid(int Payid);
   @Query("DELETE FROM location WHERE locID =:locID")
    void DeleteLocationById(int locID);

   @Query("DELETE FROM card WHERE Cardid =:CardID")
    void DeleteCardById(int CardID);




}

package com.example.pos_system.database;

import android.content.Context;
import android.icu.util.LocaleData;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pos_system.Card.CardData;
import com.example.pos_system.Customer.CustomerData;
import com.example.pos_system.Payment.PaymentData;
import com.example.pos_system.Product.ProductData;
import com.example.pos_system.Sale.SaleData;
import com.example.pos_system.User.UserData;
import com.example.pos_system.dao.UserDAO;
import com.example.pos_system.location.LocationActivity;
import com.example.pos_system.location.LocationData;
import com.example.pos_system.model.CatogoryData;

@Database(entities = {CatogoryData.class, UserData.class, ProductData.class, CustomerData.class, PaymentData.class, LocationData.class,SaleData.class, CardData.class},version = 10)
public  abstract class UserDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "bluedb";
    private static UserDatabase userDatabase;
    public static synchronized UserDatabase getUserDatabase(Context context){

        if(userDatabase == null){
            userDatabase = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

        }
        return userDatabase;
    }
    public abstract UserDAO userDAO();


}

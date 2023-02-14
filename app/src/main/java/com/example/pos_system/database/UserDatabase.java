package com.example.pos_system.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pos_system.dao.UserDAO;
import com.example.pos_system.model.User;

@Database(entities = {User.class},version = 1)
public  abstract class UserDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "bluedb";
    private static UserDatabase userDatabase;
    public static synchronized UserDatabase getUserDatabase(Context context){

        if(userDatabase == null){
            userDatabase = Room.databaseBuilder(context, UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

        }
        return userDatabase;
    }
    public abstract UserDAO userDAO();


}

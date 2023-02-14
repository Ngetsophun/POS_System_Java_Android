package com.example.pos_system.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.example.pos_system.model.User;

@Dao
public interface UserDAO {

    @Insert
    void insert(User user);

}

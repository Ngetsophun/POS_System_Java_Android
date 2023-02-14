package com.example.pos_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pos_system.dao.UserDAO;
import com.example.pos_system.database.UserDatabase;
import com.example.pos_system.model.User;

public class login_form extends AppCompatActivity {
    EditText txtUsername, txtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);


        txtUsername = findViewById(R.id.etUsername);
        txtPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setPassword(txtPassword.getText().toString());
                user.setName(txtUsername.getText().toString());

                if(validaInput(user)){
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    final UserDAO userDAO = userDatabase.userDAO();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userDAO.insert(user);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(login_form.this, "User login", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(login_form.this,MainActivity.class);
                                    startActivity(i);
                                }
                            });
                        }
                    }).start();

                }else {
                    Toast.makeText(login_form.this, "Fill all feilds!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private Boolean validaInput(User user){
        if(user.getName().isEmpty() || user.getPassword().isEmpty()){
            return false;
        }
        return true;
    }
}
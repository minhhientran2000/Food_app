package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, password,username,phone;
    Button btnRegister ;

    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        name = (EditText) findViewById(R.id.etName);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.emailTxt);
        password = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        phone = (EditText) findViewById(R.id.etPhone);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String Name = name.getText().toString();
                String Email = email.getText().toString();
                String UserName = username.getText().toString();
                String Password = password.getText().toString();
                String Phone = phone.getText().toString();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(username.getText().toString()).exists()) {
                            /*Toast.makeText(RegisterActivity.this, "User already exist !", Toast.LENGTH_SHORT).show();*/
                            username.requestFocus();
                            username.setText("");
                        }
                        else{
                            ConnectionClass user = new ConnectionClass(Name, Email, UserName, Password,Phone);
                            reference.child(UserName).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Registration successfully !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }


}
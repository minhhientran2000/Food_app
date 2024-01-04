package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CustomerProfileActivity extends AppCompatActivity {

    EditText firstname, lastname;

    TextView mobileno, Email;
    Button Update;
    LinearLayout password, LogOut;
    DatabaseReference databaseReference, data;
    FirebaseDatabase firebaseDatabase;
    String  email, passwordd, confirmpass;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageButton btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        firstname = (EditText) findViewById(R.id.fnamee);
        lastname = (EditText) findViewById(R.id.lnamee);

        Email = (TextView) findViewById(R.id.emailID);
        mobileno = (TextView) findViewById(R.id.mobilenumber);
        Update = (Button) findViewById(R.id.update);
        password = (LinearLayout) findViewById(R.id.passwordlayout);
        LogOut = (LinearLayout) findViewById(R.id.logout_layout);
        btn_back = (ImageButton) findViewById(R.id.btn_backProfile) ;

        firstname.setText(Profille.currentUser.getUsername());
        lastname.setText(Profille.currentUser.getName());
        Email.setText(Profille.currentUser.getEmail());
        mobileno.setText(Profille.currentUser.getPhone());

        updateinformation();
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerProfileActivity.this);
                builder.setMessage("Are you sure you want to Logout ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(CustomerProfileActivity.this, ChooseActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void updateinformation() {
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = FirebaseDatabase.getInstance().getReference("users").child(Profille.currentUser.getUsername());
                data.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ConnectionClass customer = dataSnapshot.getValue(ConnectionClass.class);

                        email = Profille.currentUser.getEmail();
                        passwordd = Profille.currentUser.getPassword();
                        long mobilenoo = Long.parseLong(Profille.currentUser.getPhone());
                        String Uname = firstname.getText().toString().trim();
                        String Name = lastname.getText().toString().trim();


                        HashMap<String, String> hashMappp = new HashMap<>();
                        hashMappp.put("email", email);
                        hashMappp.put("username", Uname);
                        hashMappp.put("name", Name);
                        hashMappp.put("phone", String.valueOf(mobilenoo));
                        hashMappp.put("password", passwordd);

                        firebaseDatabase.getInstance().getReference("users").child(Profille.currentUser.getUsername()).setValue(hashMappp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });






    }
}

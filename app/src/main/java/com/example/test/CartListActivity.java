package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.internal.service.Common;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartListActivity extends AppCompatActivity {
private RecyclerView.Adapter adapter;

private RecyclerView recyclerViewList;

private ManagementCart managementCart;

TextView totalFeeTxt,TaxTxt,DeliveryTxt,TotalTxt,EmptyTxt;
private double tax;

private ScrollView scrollView;
FirebaseDatabase database;
DatabaseReference request;

CartListAdapter adapter1;
TextView btnCheckout;
TinyDB tinyDB;
ArrayList<FoodDomain> Order;
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        managementCart = new ManagementCart(this);

        database = FirebaseDatabase.getInstance() ;
        request = database.getReference("Request");


        initView();

        initList();
        CalculateCart();
        bottomNavigation();

        btnCheckout = (TextView) findViewById(R.id.btnCheckOut);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order = managementCart.getListCart();
                ShowAlertDialog();
                //xoa het may o text box trong cart khi checkout xong sp

            }
        });
        ImageButton back = (ImageButton) findViewById(R.id.backCart);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void ShowAlertDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartListActivity.this);
        alertDialog.setTitle("Address ");
        alertDialog.setMessage("Enter your address");

        EditText edtAdress = new EditText(CartListActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        edtAdress.setLayoutParams(lp);
        alertDialog.setView(edtAdress);
        alertDialog.setIcon(R.drawable.logo);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String id = String.valueOf(System.currentTimeMillis());
                Request req = new Request(id, Profille.currentUser.getPhone(),Profille.currentUser.getName(),edtAdress.getText().toString(),TotalTxt.getText().toString(),Order);

                request.child(id).setValue(req);

                Toast.makeText(CartListActivity.this, "Thank you for ordered", Toast.LENGTH_SHORT).show();

                adapter= new CartListAdapter(managementCart.GetEmpty(), CartListActivity.this, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {

                    }
                });
                recyclerViewList.setAdapter(adapter);
                EmptyTxt.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);


            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }


    private void bottomNavigation()
    {
        FloatingActionButton floatingActionButton = findViewById(R.id.Cart_btn);
        LinearLayout homeBtn= findViewById(R.id.homeBtn);
        LinearLayout login= findViewById(R.id.settingBtn);
        LinearLayout Order= findViewById(R.id.supportBtn);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this,CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this,IntroActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, CustomerProfileActivity.class));

            }
        });
        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, OrderActivity.class));
            }
        });

    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerViewCate);
        totalFeeTxt=findViewById(R.id.totalFeeTxt);
        TaxTxt = findViewById(R.id.TaxTxt);
        DeliveryTxt = findViewById(R.id.DeliveryTxt);
        TotalTxt = findViewById(R.id.TotalTxt);
        EmptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView3);
        recyclerViewList = findViewById(R.id.cartView);

    }
    private void initList(){

        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter= new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });
        recyclerViewList.setAdapter(adapter);

        if(managementCart.getListCart().isEmpty())
        {
            EmptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }
        else {
            EmptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }

    }
    private void CalculateCart()
    {
        double percentTax = 0.02;
        double delivery = 5;

        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100)/100;
        double total = Math.round((managementCart.getTotalFee() + tax + delivery ) * 100)/100;
        double itemTotal = Math.round(managementCart.getTotalFee() * 100)/100;

        totalFeeTxt.setText("$" + itemTotal);
        TaxTxt.setText("$" + tax);
        DeliveryTxt.setText("$" + delivery);
        TotalTxt.setText("$"+total);

    }



}
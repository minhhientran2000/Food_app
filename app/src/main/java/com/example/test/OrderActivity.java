package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;

    private RecyclerView recyclerViewList;
    FirebaseDatabase database;
    DatabaseReference request;
    ImageButton back_btn;
    OrderView adapter2;
    TextView Orderid,Ordername,Orderaddress,Ordertotal,Orderstatus;
    ArrayList<Request> OrderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        database = FirebaseDatabase.getInstance() ;
        request = database.getReference("Request");

        initView();
        /*bottomNavigation();*/
        loadOrders(Profille.currentUser.getPhone());
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void initView() {

        Orderid = findViewById(R.id.OderID);
        Ordername = findViewById(R.id.UserN);
        Orderaddress = findViewById(R.id.Address);
        Ordertotal = findViewById(R.id.Totall);
        Orderstatus = findViewById(R.id.Status);
        recyclerViewList = findViewById(R.id.OrderRV);
        back_btn = findViewById(R.id.backBTN);

    }
    private void loadOrders(String phone) {
        OrderList = new ArrayList<>();

        request.orderByChild("phone").equalTo(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrderList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Request model = ds.getValue(Request.class);
                    OrderList.add(model);
                }
                adapter2 = new OrderView(OrderActivity.this, OrderList);
                recyclerViewList.setAdapter(adapter2);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
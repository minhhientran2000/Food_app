package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {
    private ArrayList<FoodDomain> list;
    private ArrayList<CategoryDomain> listcate;
    private RecyclerView.Adapter adapter,adapter2;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FoodDetails");

    private DatabaseReference databaseReferenceCate = FirebaseDatabase.getInstance().getReference("Category");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        LinearLayout addFood= findViewById(R.id.supportBtn);
        LinearLayout addCate = findViewById(R.id.profileBtn);
        FloatingActionButton Order = findViewById(R.id.Order_btn);
        LinearLayout btn_logout = findViewById(R.id.settingBtn);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, SanPhamAllActivity.class));
            }
        });
        addCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, DanhMucActivity.class));
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminHomeActivity.this);
                builder.setMessage("Are you sure you want to Logout ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AdminHomeActivity.this, ChooseActivity.class);
                        startActivity(intent);
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

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this,OrderAdminActivity.class));
            }
        });

        RecyclerViewCategory();
        /*recycleViewPopular();*/
    }
    private void RecyclerViewCategory()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerViewCate = (RecyclerView)findViewById(R.id.recyclerViewCate);
        recyclerViewCate.setLayoutManager(linearLayoutManager);
        listcate = new ArrayList<>();
        adapter = new CateAdapter(listcate,this);
        recyclerViewCate.setAdapter(adapter);

        databaseReferenceCate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listcate.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CategoryDomain model1 = dataSnapshot.getValue(CategoryDomain.class);
                    listcate.add(model1);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    /*private void recycleViewPopular()
    {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerViewPopularList = (RecyclerView)findViewById(R.id.recycleViewProd);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter2 = new PopularAdapter(list , this);
        recyclerViewPopularList.setAdapter(adapter2);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    FoodDomain model = dataSnapshot.getValue(FoodDomain.class);
                    list.add(model);
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }*/
}
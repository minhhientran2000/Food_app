package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter,adapter2,adapter3;

    private RecyclerView recyclerView,searchView;

    private ArrayList<FoodDomain> list,list2;
    private ArrayList<CategoryDomain> listcate;

    ConstraintLayout constraintLayout;
    TextView userName ,etSearch,showall;
    private PopularAdapter adapterPop;


    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FoodDetails");

    private DatabaseReference databaseReferenceCate = FirebaseDatabase.getInstance().getReference("Category");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        RecyclerViewCategory();
        recycleViewPopular();
        bottomNavigation();
        etSearch = findViewById(R.id.etSearch);
        constraintLayout = findViewById(R.id.constraintLayout);
        userName = findViewById(R.id.textView6);

        userName.setText(Profille.currentUser.getName());
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this,SearchActivity.class));
            }
        });
        showall = findViewById(R.id.ShowAllbtn);
        showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this,ShowProductsActivity.class));
            }
        });


    }

    private void bottomNavigation()
    {
        FloatingActionButton floatingActionButton = findViewById(R.id.Cart_btn);
        LinearLayout homeBtn= findViewById(R.id.homeBtn);
        LinearLayout login= findViewById(R.id.profileBtn);
        LinearLayout Order= findViewById(R.id.supportBtn);
        LinearLayout Profile = findViewById(R.id.settingBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this,CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this,IntroActivity.class));
            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, CustomerProfileActivity.class));

            }
        });
        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, OrderActivity.class));
            }
        });



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
    private void recycleViewPopular()
    {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerViewPopularList = (RecyclerView)findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter2 = new PopularAdapter(list , this);
        recyclerViewPopularList.setAdapter(adapter2);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
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


    }

}
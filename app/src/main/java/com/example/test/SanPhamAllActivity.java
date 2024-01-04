package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.test.databinding.ActivitySanPhamAllBinding;
import com.example.test.databinding.ActivitySanPhamBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SanPhamAllActivity extends AppCompatActivity {
    private ArrayList<FoodDomain> prodList;
    private ActivitySanPhamAllBinding binding;
    private AdapterSanPham adapterSanPham;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySanPhamAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadAllProducts();

        binding.backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.addProdBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SanPhamAllActivity.this,AddActivity.class));
            }
        });
        binding.searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    adapterSanPham.getFilter().filter(charSequence);
                }catch (Exception e){

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }
    private void loadAllProducts() {
        prodList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("FoodDetails");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prodList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    FoodDomain model = ds.getValue(FoodDomain.class);
                    prodList.add(model);
                }
                /*RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DanhMucActivity.this,RecyclerView.VERTICAL,false);
                binding.categoriesRV.setLayoutManager(layoutManager);*/
                adapterSanPham = new AdapterSanPham(SanPhamAllActivity.this, prodList);

                binding.ProdRV.setAdapter(adapterSanPham);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
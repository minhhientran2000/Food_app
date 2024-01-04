package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.test.databinding.ActivitySanPhamBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private ArrayList<FoodDomain> prodList;
    private ArrayList<CategoryDomain> categoriesArrayList;

    private String categoryId, categoryTitle;

    AdapterShow adapterShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ImageButton back = (ImageButton) findViewById(R.id.backSearch);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        EditText btnSearch = (EditText) findViewById(R.id.etSearch1);

        btnSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    adapterShow.getFilter().filter(s);

                }catch (Exception e){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loadAllProducts();


    }
    private void loadAllProducts() {
        prodList = new ArrayList<>();
        RecyclerView show = (RecyclerView) findViewById(R.id.ProdSearch1);
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
                adapterShow = new AdapterShow(SearchActivity.this, prodList);
                show.setAdapter(adapterShow);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
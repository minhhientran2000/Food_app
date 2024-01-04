package com.example.test;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    private ImageButton imageButton;
    Button post_dish,show_all;
    TextInputLayout desc,qty,pri, Dishes;
    String descrption,quantity,price,dishes, catetv, id, timestamp, cateId;
    Uri imageuri;
    TextView cateTV;

    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,dataa;
    FirebaseAuth Fauth;
    StorageReference ref;
    String ChefId , RandomUID , State, City , Area;

    private ArrayList<String> categoryTitleArrayList, categoryIdArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Dishes = (TextInputLayout)findViewById(R.id.cateName);
        desc = (TextInputLayout) findViewById(R.id.description);
        qty = (TextInputLayout) findViewById(R.id.Quantity);
        pri = (TextInputLayout) findViewById(R.id.price);
        post_dish = (Button) findViewById(R.id.postCate);
        cateTV = (TextView) findViewById(R.id.categoryProduct);
        ImageButton back = (ImageButton)findViewById(R.id.backAddProd) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Fauth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("FoodDetails");

        imageButton = (ImageButton) findViewById(R.id.cateimage_upload);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery,2);
            }
        });
        post_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageuri != null){
                    /* Id = id.getEditText().getText().toString().trim();*/
                    dishes = Dishes.getEditText().getText().toString().trim();
                    descrption = desc.getEditText().getText().toString().trim();
                    quantity = qty.getEditText().getText().toString().trim();
                    price = pri.getEditText().getText().toString().trim();
                    catetv = cateTV.getText().toString().trim();
                    timestamp = String.valueOf(System.currentTimeMillis());
                    id = timestamp;
                    cateId = String.valueOf(selectedCateId);
                    uploadToFirebase(imageuri);
                }else{
                    Toast.makeText(AddActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadCategory();
        cateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryPickDialog();
            }
        });


    }

    private void loadCategory(){
        categoryTitleArrayList = new ArrayList<>();
        categoryIdArrayList = new ArrayList<>();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Category");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryTitleArrayList.clear();
                categoryIdArrayList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    String categoryId = ""+ds.child("id").getValue();
                    String categoryTitle = ""+ds.child("title").getValue();
                    categoryTitleArrayList.add(categoryTitle);
                    categoryIdArrayList.add(categoryId);
                    /*CategoryDomain cate = ds.getValue(CategoryDomain.class);
                    categoryTitleArrayList.add(cate);*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    String selectedCateTitle, selectedCateId;
    private void categoryPickDialog(){
        String[]  categoriesArray = new String[categoryTitleArrayList.size()];
        for(int i = 0; i< categoryTitleArrayList.size(); i++){
            categoriesArray[i] = categoryTitleArrayList.get(i);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Category")
                .setItems(categoriesArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedCateTitle = categoryTitleArrayList.get(i);
                        selectedCateId = categoryIdArrayList.get(i);
                        cateTV.setText(selectedCateTitle);
                    }
                })
                .show();
    }

    private void uploadToFirebase(Uri uri){
        final ProgressDialog progressDialog = new ProgressDialog(AddActivity.this);
        progressDialog.setTitle("Uploading.....");
        progressDialog.show();
        final StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FoodDomain info = new FoodDomain(id, dishes,uri.toString(),descrption,quantity,Double.valueOf(price), timestamp,catetv, cateId);
                        databaseReference.child(id).setValue(info);
                        progressDialog.dismiss();
                        Toast.makeText(AddActivity.this,"Dish Posted Successfully!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddActivity.this,AdminHomeActivity.class));
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded "+(int) progress+"%");
                progressDialog.setCanceledOnTouchOutside(false);
            }
        });
    }


    private void Reset(){

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 2&& resultCode== RESULT_OK && data != null){
            imageuri = data.getData() ;
            imageButton.setImageURI(imageuri);
        }
    }


    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

}
package com.example.test;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterSanPham  extends RecyclerView.Adapter<AdapterSanPham.HolderCategory> implements Filterable{
    Context context;
    public ArrayList<FoodDomain> productsArrayList, filterList;
    private FilterSanPham filter;

    private ProgressDialog progressDialog;

    public AdapterSanPham(Context context, ArrayList<FoodDomain> productsArrayList) {
        this.context = context;
        this.productsArrayList = productsArrayList;
        this.filterList = productsArrayList;

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    public AdapterSanPham.HolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product,parent,false);
        return new AdapterSanPham.HolderCategory(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSanPham.HolderCategory holder, int position) {
        FoodDomain model = productsArrayList.get(position);
        String title = model.getTitle();
        String description = model.getDescription();
        String fee = String.valueOf(model.getFee());
        String category = model.getCategory();
        holder.prodTitle.setText(title);
        holder.prodDes.setText(description);
        holder.prodPrice.setText(fee);
        holder.prodCate.setText(category);
        Glide.with(context)
                .load(productsArrayList.get(position).getPic())
                .into(holder.imgview);

        holder.morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreOptionsDialog(model, holder);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(),DetailActivity.class);
                intent.putExtra("object",productsArrayList.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    private void moreOptionsDialog(FoodDomain model, HolderCategory holder) {
        String[] options = {"Edit","Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Options")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            //edit
                            Intent intent = new Intent(context, SanPhamEditActivity.class);
                            intent.putExtra("ProductId",model.getId());
                            context.startActivity(intent);
                        }
                        if(i == 1){
                            //delete
                            deleteProd(model, holder);
                        }

                    }
                })
                .show();
    }

    private void deleteProd(FoodDomain model, HolderCategory holder) {
        String prodId = model.getId();
        String prodTitle = model.getTitle();

        progressDialog.setMessage("Deleting " + prodTitle+" ...");
        progressDialog.show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("FoodDetails");
        ref.child(prodId).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new FilterSanPham(filterList, this);
        }
        return filter;
    }

    class HolderCategory extends RecyclerView.ViewHolder{
        ImageView imgview;
        ProgressBar progressBar;
        TextView prodTitle, prodDes, prodCate, prodPrice;
        ImageButton morebtn;


        public HolderCategory(@NonNull View itemView) {
            super(itemView);
            imgview = itemView.findViewById(R.id.prodView);
            prodTitle = itemView.findViewById(R.id.TitleTV);
            prodDes = itemView.findViewById(R.id.DescriptionTV);
            prodCate = itemView.findViewById(R.id.CategoryTV);
            prodPrice = itemView.findViewById(R.id.PriceTV);
            morebtn = itemView.findViewById(R.id.moreBTN);
        }
    }
}

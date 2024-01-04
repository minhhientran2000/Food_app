package com.example.test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterShow extends RecyclerView.Adapter<AdapterShow.HolderCategory>  implements Filterable {
    Context context;
    public ArrayList<FoodDomain> productsArrayList,filterlist;
    private FilterProd filter;

    public AdapterShow(Context context, ArrayList<FoodDomain> productsArrayList) {
        this.context = context;
        this.productsArrayList = productsArrayList;
        this.filterlist = productsArrayList;
    }

    @NonNull
    @Override
    public AdapterShow.HolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_allprod,parent,false);
        return new AdapterShow.HolderCategory(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterShow.HolderCategory holder, int position) {
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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(),DetailActivity.class);
                intent.putExtra("object",productsArrayList.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  productsArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
                filter = new FilterProd(filterlist, this );
        }
        return filter;
    }

    public class HolderCategory extends RecyclerView.ViewHolder {
        ImageView imgview;

        TextView prodTitle, prodDes, prodCate, prodPrice;



        public HolderCategory(@NonNull View itemView) {
            super(itemView);
            imgview = itemView.findViewById(R.id.prodView1);
            prodTitle = itemView.findViewById(R.id.TitleTV1);
            prodDes = itemView.findViewById(R.id.DescriptionTV1);
            prodCate = itemView.findViewById(R.id.CategoryTV1);
            prodPrice = itemView.findViewById(R.id.PriceTV1);

        }
    }
}

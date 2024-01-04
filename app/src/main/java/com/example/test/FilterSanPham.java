package com.example.test;

import android.widget.Filter;

import java.util.ArrayList;

public class FilterSanPham  extends Filter {
    ArrayList<FoodDomain> filterList;
    AdapterSanPham adapterSanPham;

    public FilterSanPham(ArrayList<FoodDomain> filterList, AdapterSanPham adapterSanPham) {
        this.filterList = filterList;
        this.adapterSanPham = adapterSanPham;
    }



    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        if(charSequence != null && charSequence.length() > 0){
            charSequence = charSequence.toString().toUpperCase();
            ArrayList<FoodDomain> filteredModels = new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                if(filterList.get(i).getTitle().toUpperCase().contains(charSequence)){
                    filteredModels.add(filterList.get(i));
                }
            }

            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            adapterSanPham.productsArrayList = (ArrayList<FoodDomain>) filterResults.values;
            adapterSanPham.notifyDataSetChanged();




    }

}

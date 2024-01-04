package com.example.test;

import android.widget.Filter;

import java.util.ArrayList;

public class FilterProd extends Filter {
    ArrayList<FoodDomain> filterList;

    AdapterShow adapterShow;

    public FilterProd(ArrayList<FoodDomain> filterList, AdapterShow adapterShow) {
        this.filterList = filterList;
        this.adapterShow = adapterShow;
    }
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if(constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<FoodDomain> filteredModels = new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                if(filterList.get(i).getTitle().toUpperCase().contains(constraint)){
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
    protected void publishResults(CharSequence constraint, FilterResults results) {


            adapterShow.productsArrayList  =(ArrayList<FoodDomain>)  results.values;
            adapterShow.notifyDataSetChanged();

    }
}

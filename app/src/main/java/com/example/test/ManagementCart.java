package com.example.test;

import android.content.Context;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }
    public void insertFood(FoodDomain item)
    {
        ArrayList<FoodDomain> lstFood =getListCart();
        boolean existAlready = false;
        int n = 0;
        for(int i =0;i<lstFood.size();i++)
        {
            if(lstFood.get(i).getTitle().equals(item.getTitle())){
                existAlready = true;
                n = i ;
                break;
            }
        }
        if(existAlready){
            lstFood.get(n).setNumberInCart(item.getNumberInCart());
        }
        else {
            lstFood.add(item);
        }
        tinyDB.putListObject("CartList",lstFood);
        Toast.makeText(context,"Added To Your Cart", Toast.LENGTH_LONG).show();
    }

    public ArrayList<FoodDomain> getListCart()
    {
        return tinyDB.getListObject("CartList");
    }
    public ArrayList<FoodDomain> GetEmpty()
    {
        ArrayList<FoodDomain> lstFood = new ArrayList<FoodDomain> ();
        tinyDB.putListObject("CartList",lstFood);
        return tinyDB.getListObject("CartList");

    }


    public void plusNumberFood(ArrayList<FoodDomain>ListFood,int position,ChangeNumberItemsListener changeNumberItemsListener)
    {
        ListFood.get(position).setNumberInCart(ListFood.get(position).getNumberInCart()+1);
        tinyDB.putListObject("CartList",ListFood);
        changeNumberItemsListener.changed();

    }
    public void minusNumberFood(ArrayList<FoodDomain>ListFood,int position,ChangeNumberItemsListener changeNumberItemsListener)
    {
        if(ListFood.get(position).getNumberInCart()==1)
        {
            ListFood.remove(position);
        }
        else {
            ListFood.get(position).setNumberInCart(ListFood.get(position).getNumberInCart()-1);

        }
        tinyDB.putListObject("CartList",ListFood);
        changeNumberItemsListener.changed();

    }
    public Double getTotalFee()
    {
        ArrayList<FoodDomain> listFood = getListCart() ;
        double fee = 0;
        for(int i =0; i<listFood.size();i++)
        {
            fee = fee + (listFood.get(i).getFee() *listFood.get(i).getNumberInCart());
        }
        return fee;
    }




}

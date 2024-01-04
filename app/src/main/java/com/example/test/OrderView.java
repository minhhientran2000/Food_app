package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderView extends RecyclerView.Adapter<OrderView.ViewHolder> implements Filterable {


    Context context;
    ArrayList<Request> Order,filterList;
    FilterOrder filterOrder;
    public OrderView(Context context, ArrayList<Request> Order) {
        this.context = context;
        this.Order = Order;
        this.filterList = Order;
    }


    @NonNull
    @Override
    public OrderView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order,parent,false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderView.ViewHolder holder, int position) {
        holder.Orderid.setText(Order.get(position).getId());
        holder.Ordername.setText(Order.get(position).getName());
        holder.Orderaddress.setText(Order.get(position).getAddress());
        holder.Ordertotal.setText(Order.get(position).getTotal());
        holder.Orderstatus.setText(convertCodeToStatus(Order.get(position).getStatus()));

    }
    @Override
    public Filter getFilter() {
        if(filterOrder == null){
            filterOrder = new FilterOrder(filterList, this);
        }
        return filterOrder;
    }
    private String convertCodeToStatus(String status)
    {
        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }


    @Override
    public int getItemCount() {
        return Order.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Orderid,Ordername,Orderaddress,Ordertotal,Orderstatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Orderid = itemView.findViewById(R.id.OderID);
            Ordername = itemView.findViewById(R.id.UserN);
            Orderaddress = itemView.findViewById(R.id.Address);
            Ordertotal = itemView.findViewById(R.id.Totall);
            Orderstatus = itemView.findViewById(R.id.Status);
        }
    }
}

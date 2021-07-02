package com.abdulkarim.adminapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdulkarim.adminapp.R;
import com.abdulkarim.adminapp.modal.OrderItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ChildProductAdapter extends RecyclerView.Adapter<ChildProductAdapter.CheckoutViewHolder> {

    private List<OrderItem> cartList;

    public ChildProductAdapter(List<OrderItem> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child,parent,false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {

        OrderItem orderItem = cartList.get(position);

        holder.product_name.setText("Name : "+orderItem.getName());
        holder.product_price_quantity.setText(""+orderItem.getPrice()+" X "+orderItem.getQuantity());
        holder.total_price.setText("Sub Total : "+Double.parseDouble(orderItem.getPrice())* orderItem.getQuantity());

        Picasso.get().load(orderItem.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.product_image);



    }

    
    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CheckoutViewHolder extends RecyclerView.ViewHolder {

        private ImageView product_image;
        private TextView product_name,product_price_quantity,total_price;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);

            product_image = itemView.findViewById(R.id.item_child_item_image);
            product_name = itemView.findViewById(R.id.item_child_item_name);
            product_price_quantity = itemView.findViewById(R.id.item_child_item_price_quantity_text);

            total_price = itemView.findViewById(R.id.item_child_price_text);
        }
    }
}

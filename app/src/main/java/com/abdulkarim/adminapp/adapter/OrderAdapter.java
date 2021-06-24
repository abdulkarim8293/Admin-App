package com.abdulkarim.adminapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdulkarim.adminapp.R;
import com.abdulkarim.adminapp.activity.OrderDetailsActivity;
import com.abdulkarim.adminapp.modal.MyOrder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<MyOrder> myOrderList;
    private Context context;

    public OrderAdapter(List<MyOrder> myOrderList, Context context) {
        this.myOrderList = myOrderList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_recycler_view,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderViewHolder holder, int position) {

        MyOrder myOrder = myOrderList.get(position);

        holder.order_id.setText(""+myOrder.getId());
        holder.order_price.setText(""+myOrder.getPrice());
        holder.place_date.setText(""+myOrder.getPlace_date());
        holder.order_status.setText(""+myOrder.getOrder_status());

        holder.ll_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("my_order",myOrder);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myOrderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView order_id,order_price,place_date,order_status;
        private LinearLayout ll_order;

        public OrderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            order_id = itemView.findViewById(R.id.item_order_id);
            order_price = itemView.findViewById(R.id.item_order_price);
            place_date = itemView.findViewById(R.id.item_order_place_date);
            order_status = itemView.findViewById(R.id.item_order_status);
            ll_order = itemView.findViewById(R.id.ll_order_item);

        }
    }
}

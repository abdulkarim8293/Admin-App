package com.abdulkarim.adminapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdulkarim.adminapp.R;
import com.abdulkarim.adminapp.activity.ProductListActivity;
import com.abdulkarim.adminapp.modal.Product;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_recycler_view,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Product product = productList.get(position);
        holder.product_name_text.setText(""+product.getName());
        holder.product_price_text.setText("৳ "+product.getPrice());

        double old_price = (Double.valueOf(product.getPrice())+Double.valueOf(product.getPrice())*0.25);
        int price = (int) old_price;
        holder.old_price.setText("৳ "+price);


        Picasso.get().load(product.getImage_url())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.product_image);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("id",product.getId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView product_name_text,product_price_text;
        private ImageView product_image;
        private LinearLayout linearLayout;
        private TextView old_price;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name_text = itemView.findViewById(R.id.item_product_name_text_view);
            product_price_text = itemView.findViewById(R.id.item_product_price_text_view);
            product_image = itemView.findViewById(R.id.item_image_view);
            old_price = itemView.findViewById(R.id.item_product_old_price);
            linearLayout = itemView.findViewById(R.id.item_ll);

            old_price.setPaintFlags(old_price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        }

    }
}

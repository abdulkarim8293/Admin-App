package com.abdulkarim.adminapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdulkarim.adminapp.R;
import com.abdulkarim.adminapp.modal.Product;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView product_name,product_price,product_category,product_description,product_purchase_date;
    private ImageView product_image;
    private Product product;

    SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        product = (Product) getIntent().getSerializableExtra("product");

        product_name = findViewById(R.id.product_name_text_view);
        product_price = findViewById(R.id.product_price_text_view);
        product_category = findViewById(R.id.product_category_text_view);
        product_description = findViewById(R.id.product_description_text_view);
        product_purchase_date = findViewById(R.id.product_purchase_date_text_view);
        product_image = findViewById(R.id.product_image_view);

        Picasso.get().load(product.getImage_url())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(product_image);

        product_name.setText(""+product.getName());
        product_price.setText(""+product.getPrice());
        product_category.setText(""+product.getCategory());
        product_description.setText(""+product.getDescription());

        String purchase_date = format.format(Long.parseLong(product.getPurchase_date()));
        product_purchase_date.setText(""+purchase_date);


    }
}
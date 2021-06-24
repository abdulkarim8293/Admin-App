package com.abdulkarim.adminapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abdulkarim.adminapp.R;
import com.abdulkarim.adminapp.modal.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll_product,ll_order,ll_user,ll_category;
    private TextView total_product;
    private List<Product> productList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        ll_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProductListActivity.class));
            }
        });
        ll_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,OrderActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        ll_product = findViewById(R.id.ll_product);
        ll_order = findViewById(R.id.ll_order);
        total_product = findViewById(R.id.product_count);
    }
}
package com.abdulkarim.adminapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abdulkarim.adminapp.R;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll_product,ll_order,ll_user,ll_category;
    private TextView total_product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        ll_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProductActivity.class));
            }
        });
    }

    private void init() {
        ll_product = findViewById(R.id.ll_product);
        ll_order = findViewById(R.id.ll_order);
        total_product = findViewById(R.id.product_count);
    }
}
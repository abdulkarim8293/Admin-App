package com.abdulkarim.adminapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.abdulkarim.adminapp.R;
import com.abdulkarim.adminapp.modal.MyOrder;

public class OrderDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        MyOrder myOrder = (MyOrder) getIntent().getSerializableExtra("my_order");
        Log.i("TAG",myOrder.toString());
    }
}
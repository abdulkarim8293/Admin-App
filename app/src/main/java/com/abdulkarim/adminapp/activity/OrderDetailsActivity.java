package com.abdulkarim.adminapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdulkarim.adminapp.DBHelper;
import com.abdulkarim.adminapp.R;
import com.abdulkarim.adminapp.adapter.ChildProductAdapter;
import com.abdulkarim.adminapp.modal.MyOrder;
import com.abdulkarim.adminapp.modal.OrderItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.abdulkarim.adminapp.DBHelper.firebaseFirestore;

public class OrderDetailsActivity extends AppCompatActivity {

    private TextView order_id,order_price,order_place_date,delivery_charge,order_status;
    private TextView shipping_address;
    private Button accept_button;
    private Button complete_process_button;
    private LinearLayout accept_reject_section;

    private RecyclerView recyclerView;
    private List<OrderItem> orderItemList = new ArrayList<>();
    private ChildProductAdapter childProductAdapter;

    private MyOrder myOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        myOrder = (MyOrder) getIntent().getSerializableExtra("my_order");

        order_id = findViewById(R.id.order_id);
        order_price = findViewById(R.id.order_price);
        order_place_date = findViewById(R.id.place_date);
        delivery_charge = findViewById(R.id.delivery_charge);
        order_status = findViewById(R.id.order_status);

        shipping_address = findViewById(R.id.customer_shipping_address);

        accept_button = findViewById(R.id.order_accept_button);

        recyclerView = findViewById(R.id.product_details_recycler);

        order_id.setText("Order Id "+myOrder.getId());
        order_price.setText("Price "+myOrder.getPrice());
        order_place_date.setText("Place on "+myOrder.getPlace_date());
        delivery_charge.setText("Shipping charge "+myOrder.getDelivery_charge());
        order_status.setText("Status "+myOrder.getOrder_status());

        complete_process_button = findViewById(R.id.order_complete_button);
        accept_reject_section = findViewById(R.id.accept_reject_layout);

        if (myOrder.getOrder_status().equals("delivered")){
            complete_process_button.setVisibility(View.GONE);
            accept_reject_section.setVisibility(View.GONE);
        }

        if (myOrder.getOrder_status().equals("processing")){
            complete_process_button.setVisibility(View.VISIBLE);
            accept_reject_section.setVisibility(View.GONE);
        }
        complete_process_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection(DBHelper.COLLECTION_ORDER).document(myOrder.getId())
                        .update("order_status", "delivered").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(OrderDetailsActivity.this, "Order is delivered now", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        getShippingAddress(myOrder.getId());

        orderItemList = getProductList(myOrder.getId());
        childProductAdapter = new ChildProductAdapter(orderItemList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(childProductAdapter);


        accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseFirestore.collection(DBHelper.COLLECTION_ORDER).document(myOrder.getId())
                        .update("order_status", "processing").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(OrderDetailsActivity.this, "Order is processing now", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void getShippingAddress(String order_id){

        firebaseFirestore.collection("orders").document(order_id).collection("delivery_address").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){


                    String name = documentSnapshot.getString("name");
                    String phone = documentSnapshot.getString("phone");
                    String city = documentSnapshot.getString("city");
                    String address = documentSnapshot.getString("address");
                    String zip = documentSnapshot.getString("zip");
                    String type = documentSnapshot.getString("type");

                    shipping_address.setText("Shipping of : "+type+"\nName : "+name+"\nPhone : "+phone+"\nAddress : "+address+"\nCity : "+city+" - "+zip);

                }



            }
        });

    }


    private List<OrderItem> getProductList(String order_id){

        List<OrderItem> orderItemList = new ArrayList<>();
        
        firebaseFirestore.collection("orders").document(order_id).collection("order_details").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    OrderItem orderItem = documentSnapshot.toObject(OrderItem.class);
                    orderItemList.add(orderItem);

                }
                childProductAdapter.notifyDataSetChanged();

            }
        });

        return orderItemList;
    }

}
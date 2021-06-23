package com.abdulkarim.adminapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.abdulkarim.adminapp.Collection;
import com.abdulkarim.adminapp.adapter.ProductAdapter;
import com.abdulkarim.adminapp.R;
import com.abdulkarim.adminapp.modal.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        firebaseFirestore = FirebaseFirestore.getInstance();
        getAllProduct();

        floatingActionButton = findViewById(R.id.add_product);
        recyclerView = findViewById(R.id.product_recycler);

        productAdapter = new ProductAdapter(this,productList);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductListActivity.this, AddProductActivity.class));
            }
        });


    }

    private void getAllProduct() {

        firebaseFirestore.collection(Collection.product).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Log.i("TAG", error.getMessage());
                }
                for (DocumentChange dc : value.getDocumentChanges()) {

                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        productList.add(dc.getDocument().toObject(Product.class));
                    }

                    productAdapter.notifyDataSetChanged();
                }
                Log.i("TAG","product activity product list :"+productList.size());
            }
        });
    }
}
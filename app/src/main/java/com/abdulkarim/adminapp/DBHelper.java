package com.abdulkarim.adminapp;

import android.util.Log;

import androidx.annotation.Nullable;

import com.abdulkarim.adminapp.adapter.OrderAdapter;
import com.abdulkarim.adminapp.modal.MyOrder;
import com.abdulkarim.adminapp.modal.Product;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    public static String COLLECTION_PRODUCT = "products";
    public static String COLLECTION_ORDER = "orders";

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static Task<Void> insertProduct(Product product){
        DocumentReference documentReference = firebaseFirestore.collection(COLLECTION_PRODUCT).document();
        product.setId(documentReference.getId());
        return documentReference.set(product.toMap());
    }



}

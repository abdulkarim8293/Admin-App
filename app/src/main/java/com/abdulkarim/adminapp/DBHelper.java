package com.abdulkarim.adminapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.abdulkarim.adminapp.activity.ProductActivity;
import com.abdulkarim.adminapp.modal.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DBHelper {

    static String COLLECTION_PRODUCT = "products";

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static Task<Void> insertProduct(Product product){
        DocumentReference documentReference = firebaseFirestore.collection(COLLECTION_PRODUCT).document();
        product.setId(documentReference.getId());
        return documentReference.set(product.toMap());
    }


}

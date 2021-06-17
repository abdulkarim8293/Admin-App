package com.abdulkarim.adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    private ImageView product_image;
    private EditText product_name, product_price;
    private Button save_button;

    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        product_image = findViewById(R.id.product_image_view);
        product_name = findViewById(R.id.product_name_edit_text);
        product_price = findViewById(R.id.product_price_edit_text);
        save_button = findViewById(R.id.save_button);

        product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = product_name.getText().toString().trim();
                String productPrice = product_price.getText().toString().trim();
                if (productName.equals("")){
                    Toast.makeText(AddProductActivity.this, "Please Enter Product Name", Toast.LENGTH_SHORT).show();
                }else if (productPrice.equals("")){
                    Toast.makeText(AddProductActivity.this, "Please Enter Product Price", Toast.LENGTH_SHORT).show();
                }else if (imageUri== null){
                    Toast.makeText(AddProductActivity.this, "Please Select Product Image", Toast.LENGTH_SHORT).show();
                }else {
                    uploadImage(productName,productPrice);
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                product_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //uploadImage(imageUri);
        }
    }

    private void uploadImage(String productName,String productPrice) {
        File file = new File(imageUri.getPath());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("product images").child(file.getName());
        storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = uri.toString();
                            //Log.i("TAG",""+imageUrl);
                            saveData(productName,productPrice,imageUrl);
                        }
                    });

                }
            }
        });

    }

    private void saveData(String productName, String productPrice, String imageUrl) {

        Map<String,Object> productMap = new HashMap<>();
        productMap.put("name",productName);
        productMap.put("price",productPrice);
        productMap.put("image_url",imageUrl);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection("products").document();
        productMap.put("id",documentReference.getId());
        documentReference.set(productMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddProductActivity.this, "Data upload success", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
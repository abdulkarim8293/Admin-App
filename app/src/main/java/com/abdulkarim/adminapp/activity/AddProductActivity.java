package com.abdulkarim.adminapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdulkarim.adminapp.CustomProgress;
import com.abdulkarim.adminapp.DBHelper;
import com.abdulkarim.adminapp.R;
import com.abdulkarim.adminapp.modal.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    private ImageView product_image;
    private Button save_button;
    private Uri productImageUri;
    private String productImageUrl;

    private TextInputLayout product_name,product_price,product_description,ll_product_category;
    private AutoCompleteTextView product_category;
    private Button select_purchase_date;
    private TextView purchase_date;
    private long purchaseDateInMilli;

    private CustomProgress customProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        init();

        String [] items = new String[]{
                "Category 1",
                "Category 2",
                "Category 3",
                "Category 4",
                "Category 5"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,R.layout.support_simple_spinner_dropdown_item,items
        );

        product_category.setAdapter(adapter);



        product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

        select_purchase_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickPurchaseDate();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateProductName() | !validateProductPrice() | !validateProductCategory() | !validateProductDescription() | !validateProductPurchaseDate()){
                    return;
                }else if (productImageUri == null){
                    Toast.makeText(AddProductActivity.this, "Please select product image", Toast.LENGTH_SHORT).show();

                }else{
                    // every think is ok then upload the product image and get url;
                    uploadProductImage();
                    customProgress.show();
                }

            }
        });


    }

    private void init() {

        product_image = findViewById(R.id.product_image_view);
        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_description = findViewById(R.id.product_description);
        ll_product_category = findViewById(R.id.ll_product_category);
        product_category = findViewById(R.id.product_category);
        product_category = findViewById(R.id.product_category);
        select_purchase_date = findViewById(R.id.select_purchase_date_button);
        purchase_date = findViewById(R.id.purchase_date_text);
        save_button = findViewById(R.id.save_button);

        customProgress = new CustomProgress(this);

    }

    private void pickPurchaseDate() {

        Calendar calendar = Calendar.getInstance();
        int mDate = calendar.get(Calendar.DATE);
        int mMonth = calendar.get(Calendar.MONTH);
        int mYear = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                calendar.set(year, month, date);
                SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm a");
                purchaseDateInMilli = calendar.getTimeInMillis();
                String pd = format.format(purchaseDateInMilli);
                purchase_date.setText(""+pd);

            }
        },mYear,mMonth,mDate);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    // validation function

    private boolean validateProductName(){
        String value = product_name.getEditText().getText().toString().trim();
        if (value.isEmpty()){
            product_name.setError("Field can not be empty");
            return false;
        }else {
            product_name.setError(null);
            product_name.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateProductPrice(){
        String value = product_price.getEditText().getText().toString().trim();
        if (value.isEmpty()){
            product_price.setError("Field can not be empty");
            return false;
        }else {
            product_price.setError(null);
            product_price.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateProductCategory(){
        String value = ll_product_category.getEditText().getText().toString().trim();
        if (value.isEmpty()){
            ll_product_category.setError("Field can not be empty");
            return false;
        }else {
            ll_product_category.setError(null);
            ll_product_category.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateProductDescription(){
        String value = product_description.getEditText().getText().toString().trim();
        if (value.isEmpty()){
            product_description.setError("Field can not be empty");
            return false;
        }else {
            product_description.setError(null);
            product_description.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateProductPurchaseDate(){
        String value = purchase_date.getText().toString().trim();
        if (value.isEmpty()){
            select_purchase_date.setTextColor(ContextCompat.getColor(this, R.color.colorThemeOrange));
            return false;
        }else {
            select_purchase_date.setTextColor(ContextCompat.getColor(this, R.color.black));
            return true;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            productImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), productImageUri);
                product_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadProductImage() {

        File file = new File(productImageUri.getPath());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("product images").child(file.getName());
        storageReference.putFile(productImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            productImageUrl = uri.toString();
                            insertProduct();
                        }
                    });

                }
            }
        });

    }

    private void insertProduct(){

        String name = product_name.getEditText().getText().toString().trim();
        String price = product_price.getEditText().getText().toString().trim();
        String category = product_category.getText().toString().trim();
        String product_image_url = productImageUrl;
        String description = product_description.getEditText().getText().toString().trim();
        String purchase_date = String.valueOf(purchaseDateInMilli);

        Product product = new Product(name,price,category,product_image_url,description,purchase_date);

        Log.i("TAG","Product is : "+product);

        DBHelper.insertProduct(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                customProgress.cancel();
                Toast.makeText(AddProductActivity.this, "Product upload success", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(AddProductActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
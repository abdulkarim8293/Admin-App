package com.abdulkarim.adminapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdulkarim.adminapp.DBHelper;
import com.abdulkarim.adminapp.R;
import com.abdulkarim.adminapp.adapter.OrderAdapter;
import com.abdulkarim.adminapp.modal.MyOrder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.abdulkarim.adminapp.DBHelper.firebaseFirestore;


public class ProcessingFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<MyOrder> myOrderList = new ArrayList<>();
    private OrderAdapter orderAdapter;

    public ProcessingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_processing, container, false);
        recyclerView = view.findViewById(R.id.processing_recycler);
        getMyOrders();
        orderAdapter = new OrderAdapter(myOrderList,getContext());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(orderAdapter);
    }

    private void getMyOrders() {

        firebaseFirestore.collection(DBHelper.COLLECTION_ORDER).whereEqualTo("order_status","processing").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    MyOrder order = documentSnapshot.toObject(MyOrder.class);
                    myOrderList.add(order);
                    Log.i("TAG", "order details : " + order);
                    orderAdapter.notifyDataSetChanged();

                }

                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                orderAdapter.notifyDataSetChanged();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG", "" + e.getMessage());
            }
        });
    }
}
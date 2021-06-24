package com.abdulkarim.adminapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.abdulkarim.adminapp.R;
import com.abdulkarim.adminapp.fragment.DeliveredFragment;
import com.abdulkarim.adminapp.fragment.PendingFragment;
import com.abdulkarim.adminapp.fragment.ProcessingFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class OrderActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;
    private boolean isFirstPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        chipNavigationBar = findViewById(R.id.bottom_nav_menu);

        loadFragment(new PendingFragment());
        chipNavigationBar.setItemSelected(R.id.nav_pending, true);

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int item) {

                switch (item) {
                    case R.id.nav_pending:
                        loadFragment(new PendingFragment());
                        break;
                    case R.id.nav_processing:
                        loadFragment(new ProcessingFragment());
                        break;
                    case R.id.nav_delivered:
                        loadFragment(new DeliveredFragment());
                        break;
                }

            }
        });

    }

    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }

}
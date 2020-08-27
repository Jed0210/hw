package com.example.myApp;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myApp1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QR extends FragmentActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.scanner:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Scanner()).commit();
                    return true;
                case R.id.produce:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Generator()).commit();
                    return true;
                case R.id.open:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new showQR()).commit();
                    return true;
            }
            return false;
        }
    };

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);

            setMain();

            BottomNavigationView navigation = findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }


private void setMain() {
    //getSupportFragmentManager() -> beginTransaction() -> add -> (R.id.main_boy, new Fragment()
    this.getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,new Scanner()).commit();
}

}







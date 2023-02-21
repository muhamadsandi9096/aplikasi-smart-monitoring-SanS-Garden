package com.example.sansgarden;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

//import meow.bottomnavigation.MeowBottomNavigation;
//import meow.bottomnavigation.MeowBottomNavigationKt;

public class bna extends AppCompatActivity {
    private static final String TAG = bna.class.getSimpleName();
    private final int ID_HOME = 1;
    private final int ID_NOTIFICATION = 2;
    private final int ID_SETTING = 3;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bna);

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomnavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME , R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_NOTIFICATION , R.drawable.ic_baseline_electrical_services_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_SETTING , R.drawable.ic_baseline_more_horiz_24));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                String name;
                switch (item.getId()){
                    case ID_HOME:
                        fragment = new Home();
                        break;
                    case ID_NOTIFICATION:
                        fragment = new Notification();
                        break;
                    case ID_SETTING:
                        fragment = new setting();
                        break;
                    default:
                        name = "";
                }
                if (fragment!=null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.selected_page, fragment)
                            .commit();
                }else{
                    Log.e(TAG, "Error in creating fragment");
                }
            }
        });
        bottomNavigation.show(ID_HOME , true);

        //set menu item on click
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //display a toast
                //Toast.makeText(getApplicationContext(), "You Clicked"+ item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        //set on reselect listener
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(getApplicationContext(), "You reselected"+ item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
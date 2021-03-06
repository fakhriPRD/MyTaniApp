package com.android.mytani.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.android.mytani.R;
import com.android.mytani.fragment.discover.DiscoverFragment;
import com.android.mytani.fragment.HomeFragment;
import com.android.mytani.fragment.ProfileFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class NavigationBar extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    ChipNavigationBar bottomNav;
    FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);


        bottomNav = findViewById(R.id.bottom_nav);
        if (savedInstanceState == null){
            bottomNav.setItemSelected(R.id.goTo_fragment_home, true);
            fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .commit();
        }
        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {

                // GET FRAGMENT ID TO MOVE FRAGMENT POSITION
                Fragment fragment = null;
                switch (id){
                    case R.id.goTo_fragment_home:
                    fragment = new HomeFragment();
                    break;
                    case R.id.goTo_fragment_discover:
                        fragment = new DiscoverFragment();
                        break;
                    case R.id.goTo_fragment_profile:
                        fragment = new ProfileFragment();
//                        showAllDataProfile(fragment);
                }

                // REPLACE FRAME LAYOUT WITH SELECTED FRAGMENT
                if (fragment != null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                } else{
                    Log.e(TAG, "Error dalam membuat fragment");
                }
            }
        });
    }
}
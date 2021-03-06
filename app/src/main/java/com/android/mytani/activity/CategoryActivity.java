package com.android.mytani.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.mytani.R;
import com.android.mytani.adapter.PostAdapter;
import com.android.mytani.adapter.TabCategoryAdapter;
import com.android.mytani.adapter.TabDiscoverAdapter;
import com.android.mytani.models.Post;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    // view variables
    ViewPager mPager;
    TabLayout mTabLayout;
    TabItem item_fruit, item_veggie, item_seed, item_tree;
    TabCategoryAdapter tabCategoryAdapter;

    ImageView backBtn_img;


    @Override
    public void onStart() {
        super.onStart();
        int posTab = Tabposition();
        FragmentManager fm = getSupportFragmentManager();
        tabCategoryAdapter = new TabCategoryAdapter(fm, mTabLayout.getTabCount());

        mPager.setAdapter(tabCategoryAdapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

    }

    private int Tabposition() {
        Intent intent = getIntent();
        String cat = "";
        cat = intent.getStringExtra("cat");

        switch (cat){
            case "fruit":
                return 0;
            case "veggie":
                return 1;
            case "seed":
                return 2;
            case "tree":
                return 3;
            default:
                return 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        mPager = findViewById(R.id.viewpager_category);
        mTabLayout = findViewById(R.id.tablayout_category);
        item_fruit = findViewById(R.id.tab_fruit);
        item_veggie = findViewById(R.id.tab_veggie);
        item_seed = findViewById(R.id.tab_seed);
        item_tree = findViewById(R.id.tab_tree);
        backBtn_img = findViewById(R.id.iv_cat_back);

    }

    public void goBack(View view) {
        finish();
    }
}
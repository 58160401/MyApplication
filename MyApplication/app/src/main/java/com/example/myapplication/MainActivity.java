package com.example.myapplication;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    private int[] imageUrls = new int[]{
           R.drawable.les5_1,
            R.drawable.les5_2,
            R.drawable.les5_3

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this,imageUrls);
        viewPager.setAdapter(adapter);





    }
}
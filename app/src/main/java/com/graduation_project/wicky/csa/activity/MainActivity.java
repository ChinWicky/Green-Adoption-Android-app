package com.graduation_project.wicky.csa.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.bean.Headlines;
import com.graduation_project.wicky.csa.databinding.ActivityMainBinding;
import com.graduation_project.wicky.csa.db.DatabaseHelper;
import com.graduation_project.wicky.csa.fragment.HomeFragment;
import com.graduation_project.wicky.csa.fragment.MarketFragment;
import com.graduation_project.wicky.csa.fragment.UserFragment;
import com.graduation_project.wicky.csa.utils.HawkKey;
import com.orhanobut.hawk.Hawk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MBaseActivity {

    //private TextView mTextMessage;
    private ActivityMainBinding binding;
    private boolean isUserFragment;
    private ArrayList<String> photoList = new ArrayList<>();



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(new HomeFragment());
                    return true;
                case R.id.navigation_market:
                    //replaceFragment(new MarketFragment());
                    replaceFragment(new MarketFragment());
                    //startActivity(new Intent(MainActivity.this,MarketActivity.class));
                    return true;
//                case R.id.navigation_cart:
//                    //replaceFragment(new UserFragment());
//                    return true;
                case R.id.navigation_user:
                    isUserFragment = true;
                    if (Hawk.get(HawkKey.IS_LOGIN, false)) {
                        replaceFragment(new UserFragment());
                    }else{
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        // replaceFragment(new UserFragment());
                    }
                    //startActivity(new Intent(MainActivity.this,AdoptVMActivity.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void setBaseTitle(TextView titleView) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init(savedInstanceState);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_fragment, new HomeFragment());
        transaction.commit();
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    protected void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.commit();
    }


}

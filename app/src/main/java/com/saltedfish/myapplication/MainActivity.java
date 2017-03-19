package com.saltedfish.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.saltedfish.meituan.MeiTuan;

public class MainActivity extends AppCompatActivity {

    private MeiTuan meituan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        meituan = (MeiTuan) findViewById(R.id.meituan);
        new Handler().postDelayed(()->meituan.setStateIndex(0),2000);
        new Handler().postDelayed(()->meituan.setStateIndex(1),6000);
        new Handler().postDelayed(()->meituan.setStateIndex(2),10000);
        new Handler().postDelayed(()->meituan.setStateIndex(5),14000);
    }
}

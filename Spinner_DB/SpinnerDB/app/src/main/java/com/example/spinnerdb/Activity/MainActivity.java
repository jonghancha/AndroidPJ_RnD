package com.example.spinnerdb.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spinnerdb.NetworkTask.NetworkTask;
import com.example.spinnerdb.R;
import com.example.spinnerdb.ShareVar.ShareVar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    ArrayAdapter adapter = null;
    Spinner spinner = null;

    ArrayList<String> spinnerList;

    String urlAddr = null;
    String macIP;

    String prdNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        prdNo = intent.getStringExtra("prdNo");

        // 임의로 설정.
        prdNo = "45";
        //

        macIP = ShareVar.IpAddress;
        urlAddr = "http://" + macIP + ":8080/JSP/spinner_option_list.jsp?prdNo=" + prdNo;
        spinner = findViewById(R.id.sp_01);




    }

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    private void connectGetData(){
        try {
            ///////////////////////////////////////////////////////////////////////////////////////
            // Date : 2020.12.25
            //
            // Description:
            //  - NetworkTask의 생성자 추가 : where <- "select"
            //
            ///////////////////////////////////////////////////////////////////////////////////////
            NetworkTask networkTask = new NetworkTask(MainActivity.this, urlAddr, "select");
            ///////////////////////////////////////////////////////////////////////////////////////

            Object obj = networkTask.execute().get();
            spinnerList = (ArrayList<String>) obj;
            Log.v(TAG, "spinnerList.size() : " + spinnerList.size());

            adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList);
            spinner.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
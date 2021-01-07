package com.android.firstpagead;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // Debug tag, for logging
    static final String TAG = "Main";

    // SharedPreferences 정의
    private SharedPreferences SPreferences;

    // SharedPreferences 접근 이름, 저장 데이터 초기화
    private final String NameSPreferences = "Day";
    private String strSDFormatDay = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // '오늘 그만 보기' 기능을 위한 날짜 획득
        long CurrentTime = System.currentTimeMillis(); // 현재 시간을 msec 단위로 얻음
        Date TodayDate = new Date(CurrentTime); // 현재 시간 Date 변수에 저장
        SimpleDateFormat SDFormat = new SimpleDateFormat("dd");
        strSDFormatDay = SDFormat.format(TodayDate); // 'dd' 형태로 포맷 변경

        // SharedPreferences 획득
        SPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String strSPreferencesDay = SPreferences.getString(NameSPreferences, "0");
        Log.v(TAG, strSPreferencesDay);

        // 광고 띄움
        // 오늘날짜 - 이전 날짜
        if((Integer.parseInt(strSDFormatDay) - Integer.parseInt(strSPreferencesDay)) != 0)
            StartMainAdDialog();
    }

    // 초기 실행시 광고 Dialog
    public void StartMainAdDialog() {
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        myDialogFragment.show(getSupportFragmentManager(), "MyFragment");
    }

}
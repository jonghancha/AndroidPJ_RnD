package com.example.kakaopay_rnd.Activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kakaopay_rnd.Bean.RequestData;
import com.example.kakaopay_rnd.Bean.ResponseData;
import com.example.kakaopay_rnd.NetworkTask.KakaoPayNetworkTask;
import com.example.kakaopay_rnd.R;
import com.example.kakaopay_rnd.ShareVar.ShareVar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    Button btnKakaoPay;
    String urlAddr = null;
    ArrayList<RequestData> requestData;
    ArrayList<ResponseData> responseData;
    String macIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // IP 주소 적용
        macIP = ShareVar.IpAddress;
        urlAddr = "https://kapi.kakao.com/v1/payment/ready";

        // 해쉬 키 구하기
        getHashKey();

        // 카카오페이로 결제 버튼
        btnKakaoPay = findViewById(R.id.btn_kakao_pay);
        btnKakaoPay.setOnClickListener(mClickListener);




    }

    // 해쉬 키 구하기
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

    // 카카오페이 결제버튼
    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_kakao_pay:


                    String result = connectGetData();
                    if(result.equals("1")){
                        Toast.makeText(MainActivity.this,  "결제가 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "결제가 실패 했습니다. !", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };
    // 네트워크 테스크
    private String connectGetData(){
        String result = null;
        try {

            KakaoPayNetworkTask kakaoPayNetworkTask = new KakaoPayNetworkTask(MainActivity.this, urlAddr, requestData);
            ///////////////////////////////////////////////////////////////////////////////////////

            Object obj = kakaoPayNetworkTask.execute().get();
            result = (String) obj;
//            members = (ArrayList<Student>) obj;
//            Log.v(TAG, "members.size() : " + members.size());
//
//            adapter = new StudentAdapter(SelectAllActivity.this, R.layout.student_layout, members);
//            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(onItemClickListener);
//            listView.setOnItemLongClickListener(onItemLongClickListener);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }







} // -----
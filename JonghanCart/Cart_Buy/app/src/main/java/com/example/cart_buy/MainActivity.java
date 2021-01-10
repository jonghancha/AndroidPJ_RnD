package com.example.cart_buy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ///////////////////////////////////////////////////////////////////////////
    //
    //  21.01.09 세미 생성
    //  제품 상세보기 페이지
    //
    ///////////////////////////////////////////////////////////////////////////

    final static String TAG = "ProductView";
    TextView prdName;
    String prdNo;
    Button btn_buy;
    BottomSheet bottomSheet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 연결하기
        prdName = findViewById(R.id.prdName);
        btn_buy = findViewById(R.id.btn_buy);

        // intent 가져오기
        Intent intent = getIntent();
        prdNo = Integer.toString(intent.getIntExtra("prdNo", 0));
        prdName.setText(intent.getStringExtra("prdName"));
        Log.v(TAG, "prddname ::::: "+ intent.getStringExtra("prdName") + "  prdno ::::: " + prdNo);

        // 버튼 클릭시
        btn_buy.setOnClickListener(mClickListener);

    }
    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // BottomSheet에 값 넘겨주기
//            Bundle bundle = new Bundle();
//            bundle.putString("prdNo", prdNo);
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            BottomSheet bottomSheet = new BottomSheet();
//            bottomSheet.setArguments(bundle);

            Intent intent = new Intent(MainActivity.this, BottomSheet.class);
            intent.putExtra("prdNo", prdNo);

            bottomSheet = new BottomSheet();
            bottomSheet.show(getSupportFragmentManager(),bottomSheet.getTag());
;
        }
    };

}//----------------
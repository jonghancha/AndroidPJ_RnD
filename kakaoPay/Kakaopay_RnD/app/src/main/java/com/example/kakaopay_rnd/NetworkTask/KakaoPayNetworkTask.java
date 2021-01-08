package com.example.kakaopay_rnd.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kakaopay_rnd.Bean.RequestData;
import com.example.kakaopay_rnd.Bean.ResponseData;

import java.util.ArrayList;

public class KakaoPayNetworkTask extends AsyncTask<Integer, String, Object> {

    final static String TAG = "KakaoPayNetworkTask";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<RequestData> requestDataArrayList;
    ArrayList<ResponseData> responseDataArrayList;

    public KakaoPayNetworkTask(Context context, String mAddr, ArrayList<RequestData> requestDataArrayList) {
        this.context = context;
        this.mAddr = mAddr;
        this.requestDataArrayList = requestDataArrayList;
    }

    @Override
    protected void onPreExecute() {
        Log.v(TAG, "onPreExecute()");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("카카오페이");
        progressDialog.setMessage("결제 진행중...");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.v(TAG, "onPostExecute()");
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        Log.v(TAG, "onCancelled");
        super.onCancelled();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v(TAG,"doInBackground");
        return null;
    }


}

package com.example.kakaopay_rnd.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kakaopay_rnd.Bean.RequestData;
import com.example.kakaopay_rnd.Bean.ResponseData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

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

        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        String result = null;

        try {
            Log.v(TAG, "in first try(){");
            Log.v(TAG, mAddr);

            URL url = new URL(mAddr);
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestMethod("POST");

//            // http header
            httpURLConnection.setRequestProperty("Authorization", "KakaoAK 03c51f61b2d26eef4238bccb731b83a2");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//postRequest.setHeader("Accept", "application/json");
//postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//
//
//            // request data 담기
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("cid", "TC0ONETIME")
                    .appendQueryParameter("partner_order_id", "1")
                    .appendQueryParameter("partner_user_id", "ckwhdgks")
                    .appendQueryParameter("item_name", "초코파이")
                    .appendQueryParameter("quantity", "1")
                    .appendQueryParameter("total_amount", "2200")
                    .appendQueryParameter("tax_free_amount", "0")
                    .appendQueryParameter("approval_url", "https://developers.kakao.com/success")
                    .appendQueryParameter("cancel_url", "https://developers.kakao.com/fail")
                    .appendQueryParameter("fail_url", "https://developers.kakao.com/cancel");

            String query = builder.build().getEncodedQuery();


            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            httpURLConnection.connect();
            Log.v(TAG,"connect()");
            Log.v(TAG, httpURLConnection.getResponseMessage());
            Log.v(TAG, String.valueOf(httpURLConnection.getResponseCode()));


            if(httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK){
                Log.v(TAG,"HTTP_OK");
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true){
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");
                }
                Log.v(TAG, "stringBuffer :" + stringBuffer);
                 result = parserAction(stringBuffer.toString());

                ///////////////////////////////////////////////////////////////////////////////////////

            }




        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }





        return result;
    }

    private String parserAction(String s){
        Log.v(TAG, "Parser()");
        String returnValue = null;
        try {
            Log.v(TAG, s);

            JSONObject jsonObject = new JSONObject(s);
            returnValue = jsonObject.getString("tid");


            Log.v(TAG, returnValue);


        }catch (Exception e){
            e.printStackTrace();
        }
        if (returnValue != null){
            Log.v(TAG, "returnValue = not null");
            return "1";
        }else {
            Log.v(TAG, "returnValue = null");
            return "0";
        }

    }


}

package com.example.cart_buy;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SpinnerNetworkTask extends AsyncTask<Integer, String, Object> {

    final static String TAG =  "NetworkTask";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<String> spinnerList;

    String where = null;

    public SpinnerNetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.spinnerList = new ArrayList<String>();
        this.where = where;
        Log.v(TAG, "Start : " + mAddr);
    }

    @Override
    protected void onPreExecute() {
        Log.v(TAG, "onPreExecute()");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialogue");
        progressDialog.setMessage("Get ....");
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v(TAG, "doInBackground()");

        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        String result = null;

        try{
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true) {

                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");

                }

                if (where.equals("select")){
                    parserSelect(stringBuffer.toString());
                }else {
                    //result = parserAction(stringBuffer.toString());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(bufferedReader != null) bufferedReader.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(inputStream != null) inputStream.close();

            }catch (Exception e2){
                e2.printStackTrace();
            }
        }

        if (where.equals("select")){
            return spinnerList;
        }else {
            return result;
        }

    }

    @Override
    protected void onPostExecute(Object o) {
        Log.v(TAG, "onPostExecute()");
        super.onPostExecute(o);
        progressDialog.dismiss();
    }
    @Override
    protected void onCancelled() {
        Log.v(TAG,"onCancelled()");
        super.onCancelled();
    }

    private void parserSelect(String s){
        Log.v(TAG, "Parser()");

        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("options_info"));
            spinnerList.clear();

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String option = jsonObject1.getString("option");

                Log.v(TAG, "option : " + option);

                spinnerList.add(option);
                Log.v(TAG, spinnerList.toString());
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    private String parserAction(String s){
//        Log.v(TAG,"Parser()");
//        String returnValue = null;
//
//        try {
//            Log.v(TAG, s);
//
//            JSONObject jsonObject = new JSONObject(s);
//            returnValue = jsonObject.getString("result");
//            Log.v(TAG, returnValue);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return returnValue;
//    }

}//---------

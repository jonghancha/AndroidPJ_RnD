package com.khankong.recyclercardwebview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    /////////////////////////////////////////////////////////////////////////////////////
    //
    // 필요하면 이 부분은 Database나 ArrayList에서 불러서 처리한다.
    //
    /////////////////////////////////////////////////////////////////////////////////////
    private String[] titles = {"강아지 1", "강아지 2", "집안","Monday 월", "Tuesday 화", "Wednesday 수", "Thursday 목",
            "Friday 금", "Saturday 토", "Sunday 일" };
    private String[] details = {"강아지 1 사진", "강아지 2 사진", "집안 사진","월요일 기상정보","화요일 기상정보","수요일 기상정보",
            "목요일 기상정보", "금요일 기상정보","토요일 기상정보","일요일 기상정보"};
    private String[] images = {"dog1.jpg","dog2.jpg","room.jpg", "w1.jpg", "w2.jpg", "w3.jpg" ,"w4.jpg","w5.jpg","w6.jpg","w7.jpg"};

    public class ViewHolder extends RecyclerView.ViewHolder{

        public WebView webView;
        public TextView itemTitle;
        public TextView itemDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            webView = itemView.findViewById(R.id.webview);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDetail = itemView.findViewById(R.id.item_detail);

            // Web Setting
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true); // JavaScript 사용가능
            webSettings.setBuiltInZoomControls(true); // 확대 축소 가능
            webSettings.setDisplayZoomControls(false); // 돋보기 없애기

            // webview의 배경 투명으로 전환
            webView.setBackgroundColor(Color.TRANSPARENT);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    /////////////////////////////////////////////////////////
                    //
                    // Snackbar는 화면 하단에 Toast같이 간단한 정보를 보여주는 Dialog
                    //
                    /////////////////////////////////////////////////////////
                    Snackbar.make(v, details[position], Snackbar.LENGTH_LONG).setAction("", null).show();
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.itemTitle.setText(titles[position]);
        holder.itemDetail.setText(details[position]);
        holder.webView.loadData(htmlData(images[position]), "text/html", "UTF-8");
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    // web server에서 filename으로 image file 불러오기
    private String htmlData(String location){
        String htmlData = "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "</head>" +
                "<body><center>" +
                "<img src = \"http://192.168.219.101:8080/images/" + location + "\"style=\"width: auto; height: 100%;\"" +
                "</center></body>" +
                "</html>";
        return htmlData;
    }
} // -------

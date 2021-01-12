package com.android.hybrid01;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    WebView webView = null;
    Button btnReload, btnPage1, btnPage2, btnPage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML과 연결
        webView = findViewById(R.id.webView);
        btnReload = findViewById(R.id.btn_reload);
        btnPage1 = findViewById(R.id.btn_page1);
        btnPage2 = findViewById(R.id.btn_page2);
        btnPage3 = findViewById(R.id.btn_page3);


        addListener();

        // Web Setting
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 자바 스크립트는 쓰겠다.
        webSettings.setBuiltInZoomControls(true); // 확대 축소 기능
        webSettings.setDisplayZoomControls(false); // 돋보기 없애기
        webView.setBackgroundColor(Color.TRANSPARENT);  // webview의 배경 투명으로 전환


        // Link시 다른 Browser 안보이게
        webView.setWebViewClient(new WebViewClient(){
            // 웹페이지로 갔을 때
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                btnReload.setText("로딩 중 ..."); // 버튼에 로딩중을 띄우겠다
            }

            // 웹페이지 다 불러왔을 때
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                btnReload.setText(webView.getTitle()); // html 위에 썼던 타이틀 불러오기
            }
        });




        String URL = "http://112.170.44.211:8080/test/20210111542309.jpg";

        String style = "<style>img{ max-width: 100%; height: auto;}</style>";
        String html = "<html><body><img src=\"" + URL + "\"/></body></html>";

        String htmlData = "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "</head>" +
                "<body><center>" +
                "<img src = \"" + URL + "\"style=\"width: auto; height: 90%;\">" +
                "</center></body>" +
                "</html>";
        webView.loadData(htmlData,"text/html", "UTF-8");


    }

    // 백스페이스 눌렀을 때 어떻게 할지
    // 꼭 있어야 함
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (webView.canGoBack()){
            webView.goBack(); // go back 할 수 있으면 go back 해라
        }else {
            finish();
        }
    }

    private void addListener(){
        btnReload.setOnClickListener(onClickListener);
        btnPage1.setOnClickListener(onClickListener);
        btnPage2.setOnClickListener(onClickListener);
        btnPage3.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_reload:
                    btnReloadClick();
                    break;
                case R.id.btn_page1:
                    btnPage1Click();
                    break;
                case R.id.btn_page2:
                    btnPage2Click();
                    break;
                case R.id.btn_page3:
                    btnPage3Click();
                    break;
            }
        }
    };



    private void btnReloadClick(){
        webView.loadUrl("http://192.168.0.54:8080/test/Arithmetic.jsp");
    }

    private void btnPage1Click(){
        webView.loadUrl("http://192.168.0.54:8080/test/Arithmetic.jsp");
    }

    private void btnPage2Click(){
        webView.loadUrl("http://192.168.0.54:8080/test/ResponseAge_01.jsp");
    }

    private void btnPage3Click(){
        webView.loadUrl("http://192.168.0.54:8080/test/Quiz02.html");
    }



















} //----
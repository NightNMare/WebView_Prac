package com.example.test14;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    Button btn1, btn2, btn3, btn4;
    WebView webView;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        tv = findViewById(R.id.text1);
        webView = findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/sunrin.html");

        webView.addJavascriptInterface(new MyScript(), "android");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new myChromeClient());

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnclicked(btn1.getText().toString());
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnclicked(btn2.getText().toString());
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnclicked(btn3.getText().toString());
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnclicked(btn4.getText().toString());
            }
        });
    }

    void btnclicked(final String a) {
        tv.setText(a + "");
        webView.loadUrl("javascript:change('" + a + "')");
        webView.setWebViewClient(new myWebViewClient());
    }

    class MyScript {
        @JavascriptInterface
        public String getUrl() {
            return "http://sunrin.graphics";
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            tv.setText("정보보호과");
        } else {
            super.onBackPressed();
        }
    }

    class myChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            result.confirm();
            return true;
        }
    }

    private class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            view.loadUrl(url);
            return true;
        }
    }
}

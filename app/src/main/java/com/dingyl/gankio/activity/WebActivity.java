package com.dingyl.gankio.activity;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dingyl.gankio.R;

public class WebActivity extends AppCompatActivity {

    private static final String TAG = WebActivity.class.getSimpleName();
    private WebView webView;
    private ProgressBar progressBar;
    private ActionBar actionBar;
    private String url;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        initLoad();
    }

    private void initView(){

        url = getIntent().getStringExtra("web_url");
        title = getIntent().getStringExtra("desc");
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progress_bar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @JavascriptInterface
    private void initLoad(){

        webView.loadUrl(url);
        webView.addJavascriptInterface(this,"android");
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
    }

    private WebViewClient webViewClient = new WebViewClient(){

        @Override
        public void onPageFinished(WebView webView,String url){
            progressBar.setVisibility(View.GONE);
            Log.d(TAG,"onPageFinished");
        }

        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon){
            progressBar.setVisibility(View.VISIBLE);
            Log.d(TAG,"onPageStarted");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.equals("http://www.google.com/")){
                Toast.makeText(WebActivity.this,"国内不能访问google,拦截该url",Toast.LENGTH_LONG).show();
                return true;//表示我已经处理过了
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient(){

        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult jsResult){
            AlertDialog.Builder builder = new AlertDialog.Builder(webView.getContext());
            builder.setMessage(message);
            builder.setCancelable(false);
            builder.create().show();
            jsResult.confirm();//处理结果为确定状态同时唤醒WebCore线程,处理结果为确定状态同时唤醒WebCore线程
            return true;
        }

        @Override
        public void onReceivedTitle(WebView webView,String title){
            super.onReceivedTitle(webView,title);
        }

        @Override
        public void onProgressChanged(WebView webView,int newProgress){
            progressBar.setProgress(newProgress);
            Log.d(TAG,"onProgressChanged");
            Log.d(TAG,"newProgress is : " + newProgress);
        }
    };

    @Override
    public boolean  onKeyDown(int keycode,KeyEvent event){
        if(webView.canGoBack() && keycode == KeyEvent.KEYCODE_BACK){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keycode,event);
    }

    @JavascriptInterface
    public void getClient(String str){

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        webView.destroy();
        webView = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

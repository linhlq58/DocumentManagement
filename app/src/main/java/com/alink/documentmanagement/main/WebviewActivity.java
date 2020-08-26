package com.alink.documentmanagement.main;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alink.documentmanagement.appConfig.AppConfig;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebviewActivity extends AppCompatActivity {
    public WebView webView;
    public String url;
    public String doc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = getIntent().getStringExtra(AppConfig.FILE_URL);

        String mimeSubString = url.substring(url.lastIndexOf(".") + 1).trim();

        webView = new WebView(this);

        if (!mimeSubString.equals("pdf")) {
            doc="<iframe src='http://view.officeapps.live.com/op/embed.aspx?src=" + url + "'"+
                    " width='100%' height='100%' style='border: none;'></iframe>";
        } else {
            doc="<iframe src='http://docs.google.com/viewer?url=" + url + "&embedded=true'"+
                    " width='100%' height='100%' style='border: none;'></iframe>";
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowFileAccess(true);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

        });
        //webView.loadUrl("http://mobile.labdieuhanh.ungdungonline.vn/data/open/CV%20tập%20huấn%20CB%20quản%20lý.doc");
        webView.loadData( doc , "text/html",  "UTF-8");

        setContentView(webView);
    }
}

package in.mobicomly.download.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.steamcrafted.loadtoast.LoadToast;

import in.mobicomly.download.R;
import in.mobicomly.download.common.BaseActivity;

public class BrowseActivity extends BaseActivity {
    private WebView webView;

    private LoadToast lt;
    private String btUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        super.setTopBarTitle("详情页面");
        webView = findViewById(R.id.web_view);
        Intent getIntent = getIntent();
        btUrl=getIntent.getStringExtra("url");
        lt = new LoadToast(this);
        webView.setWebChromeClient(new MyWebChromeClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.supportMultipleWindows();
        webSettings.setAllowContentAccess(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(false);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(btUrl);
        lt.setTranslationY(200).setBackgroundColor(getResources().getColor(R.color.colorMain)).setProgressColor(getResources().getColor(R.color.white));
        lt.show();
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view,int newProgress){
            if(100==newProgress){
                lt.success();
                lt.hide();
            }
        }
    }

}

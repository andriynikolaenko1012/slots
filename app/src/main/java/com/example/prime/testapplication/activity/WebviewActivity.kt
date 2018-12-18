package com.example.prime.testapplication.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.prime.testapplication.R
import kotlinx.android.synthetic.main.webview_activity.*


class WebviewActivity : FragmentActivity() {

    private var url: String? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_activity)

//        val bundle = intent.extras
//        if (bundle != null){
//            url = bundle.getString("url")
//        }

        url = "http://clickmonitor.website/139177"

        webview.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        webview.settings.javaScriptEnabled = true
        webview.settings.javaScriptCanOpenWindowsAutomatically = true

        webview.settings.domStorageEnabled = true
        webview.settings.databaseEnabled = true
//        webview.settings.setSupportMultipleWindows(true)
        webview.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webview.settings.setAppCacheEnabled(true)
        webview.settings.loadWithOverviewMode = true
        webview.settings.useWideViewPort = true
        webview.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY

        webview.loadUrl(url)

        webview.webChromeClient = WebChromeClient()
        webview.webViewClient = WebViewClient()

        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        webview.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        webview.restoreState(savedInstanceState)
    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
        } else {
            super.onBackPressed()
        }
    }
}

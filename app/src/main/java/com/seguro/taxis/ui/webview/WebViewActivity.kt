package com.seguro.taxis.ui.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import com.seguro.taxis.ui.base.BaseActivity
import com.seguro.taxis.manager.SharedPreferencesManager
import com.seguro.taxis.R
import kotlinx.android.synthetic.main.activity_web_view.animation_loader_web
import kotlinx.android.synthetic.main.activity_web_view.webViewMap

class WebViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        loadWebView()
    }

    override fun init() {}

    private fun loadWebView() {
        val preferences = SharedPreferencesManager(this@WebViewActivity)
        val webStore = preferences.getWeStore()
        loadLink(webStore)
    }

    override fun showLoader() {
        if (animation_loader_web != null) {
            animation_loader_web.visibility = View.VISIBLE
            animation_loader_web.playAnimation()
        }
    }

    override fun hideLoader() {
        if (animation_loader_web != null) {
            animation_loader_web.cancelAnimation()
            animation_loader_web.visibility = View.GONE
        }
    }

    private fun loadLink(url: String) {
        val webSetting = webViewMap.settings
        webSetting.javaScriptEnabled = true
        webViewMap.webViewClient = WebViewClient()
        var newUrl = url
        if (!url.contentEquals("http://")) {
            newUrl = url
        }
        webViewMap.loadUrl(newUrl)
    }

    inner class WebViewClient : android.webkit.WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            showLoader()
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            hideLoader()
        }
    }
}

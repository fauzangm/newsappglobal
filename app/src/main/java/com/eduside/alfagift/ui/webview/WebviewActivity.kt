package com.eduside.alfagift.ui.webview

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.eduside.alfagift.databinding.ActivityWebviewBinding
import com.eduside.alfagift.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import splitties.toast.toast
import timber.log.Timber

@AndroidEntryPoint
class WebviewActivity : AppCompatActivity() {
    private var _binding: ActivityWebviewBinding? = null
    private val binding get() = _binding!!

    companion object {
        val URL = "URL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            initUi()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initUi() {
        EspressoIdlingResource.increment()
        binding.webView.webViewClient = CustomWebViewClient(binding.pbLoading)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.addJavascriptInterface(HtmlReaderInterface(), "Subsciribe")
        binding.webView.loadUrl(intent.getStringExtra(URL).toString())
        binding.webView.settings.setSupportZoom(true)


    }


    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }

    }

    private inner class HtmlReaderInterface() {
        @JavascriptInterface
        fun onSuccessMessage(html: String) {
            Timber.e(html)
            Timber.e("onsucces message")
            if (html.contains("\"product__single product type-product post-16 status-publish first instock product_cat-subscriptions has-post-thumbnail sold-individually purchasable product-type-variable-subscription")) {
                toast("Berhasil Close")
                finish()
            } else if (html.contains("class=\"woo-content container\"")) {
                toast("Gagal Close")
                finish()
            }
        }
    }


}
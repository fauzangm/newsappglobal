package com.eduside.alfagift.ui.webview

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.eduside.alfagift.utils.EspressoIdlingResource

class CustomWebViewClient(private val spinner: ProgressBar): WebViewClient() {
    private var showOrHideWebViewInitialUse = "show"

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        if(showOrHideWebViewInitialUse == "show") {
            view?.visibility = WebView.INVISIBLE

        }
    }

    override fun onPageFinished(view: WebView, url: String) {
        showOrHideWebViewInitialUse = "hide"
        spinner.visibility = View.GONE
        EspressoIdlingResource.decrement()
        view.visibility = WebView.VISIBLE
        view.loadUrl(
            "javascript:window.Subsciribe.onSuccessMessage" +
                    "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');"
        )
        super.onPageFinished(view, url)

    }
}
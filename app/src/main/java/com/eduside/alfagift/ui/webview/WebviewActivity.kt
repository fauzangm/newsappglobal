package com.eduside.alfagift.ui.webview

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduside.alfagift.databinding.ActivityMainBinding
import com.eduside.alfagift.databinding.ActivitySplashBinding
import com.eduside.alfagift.databinding.ActivityWebviewBinding
import com.eduside.alfagift.ui.chanelBerita.ChannelActivity
import com.eduside.alfagift.ui.chanelBerita.ChannelViewModel
import com.eduside.alfagift.ui.listBerita.ListBeritaActivity
import com.eduside.alfagift.utils.showError
import com.eduside.alfagift.utils.showLoading
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

@AndroidEntryPoint
class WebviewActivity : AppCompatActivity() {
    private var _binding: ActivityWebviewBinding? = null
    private val binding get() = _binding!!

    companion object{
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

    private fun initUi() {
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(intent.getStringExtra(URL).toString())
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)

    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()){
            binding.webView.goBack()
        }else{
            super.onBackPressed()
        }

    }


}
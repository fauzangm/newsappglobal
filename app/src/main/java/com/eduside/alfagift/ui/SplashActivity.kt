package com.eduside.alfagift.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduside.alfagift.databinding.ActivityMainBinding
import com.eduside.alfagift.databinding.ActivitySplashBinding
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
class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: ChannelViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            initUi()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initUi() {


        viewmodel.getBerita()
        initObserve()
    }

    private fun initObserve() {
        viewmodel.getBeritaError.observe(this) {
        }
        viewmodel.getBeritaLoading.observe(this) {

        }
        viewmodel.getBeritaResponse.observe(this) {
            startActivity(Intent(this,ChannelActivity::class.java))
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, ChannelActivity::class.java))
            finish()
        },2500)
    }

}
package com.eduside.alfagift.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.eduside.alfagift.R
import com.eduside.alfagift.databinding.ActivityMainBinding
import com.eduside.alfagift.utils.showError
import com.eduside.alfagift.utils.showLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewmodel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding  = ActivityMainBinding.inflate(layoutInflater)
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
        initAction()
    }

    private fun initObserve() {
        viewmodel.getBeritaError.observe(this) {
            showError(this, it)
        }
        viewmodel.getBeritaLoading.observe(this) {
            binding.pbSubmitRegistrasi.visibility = View.VISIBLE
            showLoading(this, binding.pbSubmitRegistrasi, it)


        }
        viewmodel.getBeritaResponse.observe(this) {
            Log.e("dapatdata",it.articles.toString())
        }

    }

    private fun initAction() {

    }
}
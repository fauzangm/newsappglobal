package com.eduside.alfagift.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduside.alfagift.R
import com.eduside.alfagift.databinding.ActivityMainBinding
import com.eduside.alfagift.utils.showError
import com.eduside.alfagift.utils.showLoading
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewmodel : MainViewModel by viewModels()
    @Inject lateinit var adapter: MainActivityAdapter
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
        binding.rvListBerita.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvListBerita.adapter = adapter
        viewmodel.getBerita()
        viewmodel.getSumber()
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

        viewmodel.readSumber.observe(this){
            adapter.submitList(it)
        }

    }

    private fun initAction() {

    }
}
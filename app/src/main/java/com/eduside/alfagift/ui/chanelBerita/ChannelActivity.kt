package com.eduside.alfagift.ui.chanelBerita

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduside.alfagift.databinding.ActivityMainBinding
import com.eduside.alfagift.ui.listBerita.ListBeritaActivity
import com.eduside.alfagift.utils.showError
import com.eduside.alfagift.utils.showLoading
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

@AndroidEntryPoint
class ChannelActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: ChannelViewModel by viewModels()

    @Inject
    lateinit var adapter: ChannelAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
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
            Log.e("dapatdata", it.articles.toString())
        }

        viewmodel.readSumber.observe(this) {
            adapter.submitList(it)
        }

    }

    @Subscribe
    fun onItemClickedEvent(event: ItemDataChannelEvent) {
        val intent = Intent(this, ListBeritaActivity::class.java)
        intent.putExtra(ListBeritaActivity.SUMBER, event.data.sumber)
        startActivity(intent)
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }




}
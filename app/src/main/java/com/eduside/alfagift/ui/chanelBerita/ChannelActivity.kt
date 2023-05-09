package com.eduside.alfagift.ui.chanelBerita

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduside.alfagift.R
import com.eduside.alfagift.databinding.ActivityMainBinding
import com.eduside.alfagift.ui.listBerita.ListBeritaActivity
import com.eduside.alfagift.utils.EspressoIdlingResource
import com.eduside.alfagift.utils.IsSync
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
    private val viewmodelBerita: BeritaViewModel by viewModels()
    private lateinit var dialog : Dialog

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
        if (IsSync == "0"){
            viewmodel.getBerita()
        }
        viewmodel.getSumber()
        initObserve()
        initAction()
        initDialog()
    }

    private fun initAction() {
        binding.btnSinkronData.setOnClickListener {
            dialog.show()
            EspressoIdlingResource.increment()
            viewmodelBerita.getSyncBerita()
        }
    }
    private fun initDialog() {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    private fun initObserve() {
        viewmodelBerita.getBeritaError.observe(this) {
            EspressoIdlingResource.decrement()
            showError(this, it)
        }
        viewmodelBerita.getBeritaLoading.observe(this) {
            showLoading(this, dialog, it)


        }
        viewmodelBerita.getBeritaResponse.observe(this) {
            EspressoIdlingResource.decrement()
            IsSync = "1"
            startActivity(Intent(this@ChannelActivity,ChannelActivity::class.java))
            finish()
            Log.e("dapatdata", it.articles.toString())
        }

        viewmodel.getBeritaError.observe(this) {
            showError(this, it)
        }
        viewmodel.getBeritaLoading.observe(this) {
            showLoading(this, dialog, it)


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


    override fun onDestroy() {
        super.onDestroy()
        dialog.dismiss()
    }


}
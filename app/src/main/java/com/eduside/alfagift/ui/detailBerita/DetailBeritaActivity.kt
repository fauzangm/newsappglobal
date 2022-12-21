package com.eduside.alfagift.ui.detailBerita

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.ColumnInfo
import com.bumptech.glide.Glide
import com.eduside.alfagift.databinding.ActivityDetailBinding
import com.eduside.alfagift.databinding.ActivityListBeritaBinding
import com.eduside.alfagift.databinding.ActivityMainBinding
import com.eduside.alfagift.ui.chanelBerita.ChannelActivity
import com.eduside.alfagift.ui.webview.WebviewActivity
import com.eduside.alfagift.utils.showError
import com.eduside.alfagift.utils.showLoading
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailBeritaActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    companion object{
        val SUMBER = "SUMBER"
        val URL = "URL"
        val URLIMAGE = "URLIMAGE"
        val AUTHOR = "AUTHOR"
        val DESCRIPTION = "DESCRIPTION"
        val CONTENT = "CONTENT"
        val TITTLE = "TITTLE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding  = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            initUi()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun initUi() {

        binding.etSUmber.text = intent.getStringExtra(SUMBER).toString()
        binding.etContent.text = intent.getStringExtra(CONTENT).toString()
        binding.etDescription.text = intent.getStringExtra(DESCRIPTION).toString()
        binding.etLinkUrl.text = intent.getStringExtra(URL).toString()
        binding.tvTitle.text = intent.getStringExtra(TITTLE).toString()
        binding.tvAuthor.text = intent.getStringExtra(AUTHOR).toString()

        Glide
            .with(this)
            .load(intent.getStringExtra(URLIMAGE).toString())
            .centerCrop()
            .into(binding.imgTittle)
        initAction()
    }


    private fun initAction() {

        binding.etLinkUrl.setOnClickListener {
            val url = intent.getStringExtra(URL).toString()
            val intent = Intent(this,WebviewActivity::class.java)
            intent.putExtra(WebviewActivity.URL,url)
            startActivity(intent)
        }
        binding.imgback.setOnClickListener {
            onBackPressed()
        }
        binding.btnHome.setOnClickListener {
            startActivity(Intent(this, ChannelActivity::class.java))
        }
    }



}
package com.eduside.alfagift.ui.listBerita

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduside.alfagift.databinding.ActivityListBeritaBinding
import com.eduside.alfagift.databinding.ActivityMainBinding
import com.eduside.alfagift.ui.chanelBerita.ItemDataChannelEvent
import com.eduside.alfagift.ui.detailBerita.DetailBeritaActivity
import com.eduside.alfagift.utils.showError
import com.eduside.alfagift.utils.showLoading
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

@AndroidEntryPoint
class ListBeritaActivity : AppCompatActivity(),SearchView.OnQueryTextListener {
    private var _binding: ActivityListBeritaBinding? = null
    private val binding get() = _binding!!
    private var sumber = ""
    private val viewmodel : ListBeritaViewModel by viewModels()
    @Inject lateinit var adapter: ListBeritaAdapter

    companion object{
        val SUMBER = "SUMBER"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding  = ActivityListBeritaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            initUi()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun initUi() {

        sumber = intent.getStringExtra(SUMBER).toString()
        binding.tvTitle.text = sumber
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if(query != null){
                    searchDatabase(query)
                }
                return true
            }

        })

        binding.rvListBerita.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvListBerita.adapter = adapter
        initObserve()
        initAction()
    }

    private fun initObserve() {

        viewmodel.getListberita(sumber).observe(this){
            adapter.submitList(it)
        }

    }


    private fun initAction() {

        binding.imgback.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        if (newText != null) {
            searchDatabase(newText)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        viewmodel.searchDataBerita(searchQuery,sumber).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }
        }
    }

    @Subscribe
    fun onItemClickedEvent(event: ItemDataBeritaEvent) {
        val intent = Intent(this, DetailBeritaActivity::class.java)
        intent.putExtra(DetailBeritaActivity.SUMBER, event.data.sumber)
        intent.putExtra(DetailBeritaActivity.URL, event.data.url)
        intent.putExtra(DetailBeritaActivity.URLIMAGE, event.data.urlToImage)
        intent.putExtra(DetailBeritaActivity.TITTLE, event.data.title)
        intent.putExtra(DetailBeritaActivity.DESCRIPTION, event.data.description)
        intent.putExtra(DetailBeritaActivity.AUTHOR, event.data.author)
        intent.putExtra(DetailBeritaActivity.CONTENT, event.data.content)
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
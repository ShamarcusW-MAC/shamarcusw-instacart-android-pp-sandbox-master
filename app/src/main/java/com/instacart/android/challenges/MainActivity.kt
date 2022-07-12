package com.instacart.android.challenges

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.instacart.android.challenges.network.OrderRepositoryImpl
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {

    private val factoryViewModel = object: ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainActivityViewModel(application, OrderRepositoryImpl(), Dispatchers.IO) as T
        }
    }

    private val viewModel: MainActivityViewModel by viewModels { factoryViewModel }

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView

    private val adapterItem: ItemAdapter by lazy {
        ItemAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()

        viewModel.setStateUpdateListener(object : MainActivityViewModel.UpdateListener {
            override fun onUpdate(state: ItemListViewState) = renderItemList(state)
            override fun onLoading() {

            }

            override fun onError(error: Exception) {
            }
        })
    }

    private fun renderItemList(state: ItemListViewState) {
        adapterItem.update(state.items)
        toolbar.title = state.toolbarTitle
        setSupportActionBar(toolbar)
    }

    private fun bindViews() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = adapterItem
        }
    }
}

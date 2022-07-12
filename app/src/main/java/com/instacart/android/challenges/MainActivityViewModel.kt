package com.instacart.android.challenges

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.instacart.android.challenges.network.OrderRepository
import com.instacart.android.challenges.network.OrderRepositoryImpl
import com.instacart.android.challenges.network.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel(
    application: Application,
    private val ordersRepository: OrderRepository = OrderRepositoryImpl(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AndroidViewModel(application) {

    interface UpdateListener {
        fun onUpdate(state: ItemListViewState)
        fun onLoading()
        fun onError(error: Exception)
    }

    private var listener: UpdateListener? = null

    init {

    }

    fun setStateUpdateListener(listener: UpdateListener?, orderId: Long = 1) {
        this.listener = listener

        viewModelScope.launch(ioDispatcher) {
            ordersRepository.getOrderById(orderId).collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is ResultState.LOADING -> {
                            listener?.onLoading()
                        }
                        is ResultState.SUCCESS -> {
                            val stateItems = ItemListViewState("Delivery Items", it.response.items.mapToItemRow())
                            listener?.onUpdate(stateItems)
                        }
                        is ResultState.ERROR -> {
                            listener?.onError(it.error)
                        }
                    }
                }

            }
        }


    }
}

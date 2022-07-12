package com.instacart.android.challenges

import com.instacart.android.challenges.network.DeliveryItem


data class ItemListViewState(
    val toolbarTitle: String,
    val items: List<ItemRow>
)

data class ItemRow(val name: String, val quantity: Int)

fun List<DeliveryItem>.mapToItemRow(): List<ItemRow> {
    return this.map {
        ItemRow(
            name = it.name,
            quantity = it.count
        )
    }
}
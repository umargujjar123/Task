package com.example.basearchitectureproject.base

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("adapterData")
fun <T> RecyclerView.bindDataSet(data: List<T>?) {
    adapter?.let {
        if (it is ListAdapter<*, in RecyclerView.ViewHolder>) {
            val tempAdapter = it as ListAdapter<T, in RecyclerView.ViewHolder>
            tempAdapter.submitList(data)
        } else if (it is RecyclerView.Adapter<in RecyclerView.ViewHolder>) {
            if (it is ConcatAdapter) {
                it.adapters.forEach { adapter ->
                    if (adapter is BaseRecyclerViewAdapter<*, *>) {
                        var productsAdapter =
                            adapter as? BaseRecyclerViewAdapter<in RecyclerView.ViewHolder, T>
                        productsAdapter?.let {
                            it.submitList(data ?: emptyList())
                        }
                    }
                }
            } else if (it is BaseRecyclerViewAdapter<*, *>) {
                val tempAdapter = it as BaseRecyclerViewAdapter<in RecyclerView.ViewHolder, T>
                tempAdapter.submitList(data ?: emptyList())
            }
        }
    }
}
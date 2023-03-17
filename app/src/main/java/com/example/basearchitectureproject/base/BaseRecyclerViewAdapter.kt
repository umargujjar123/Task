package com.example.basearchitectureproject.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<R: RecyclerView.ViewHolder, T> : RecyclerView.Adapter<R>() {

    private val itemsList: MutableList<T> = mutableListOf()

    /**
     * Adds the new [list] in the existing items list of Adapter.
     * @param The new list to add
     */
    open fun submitList(list: List<T>) {
        itemsList.addAll(list)
        notifyDataSetChanged()
    }

    /**
     * Clears the old list and adds the new [list] as fresh.
     * @param The list to replace the old list with.
     */
    open fun replaceList(list: List<T>) {
        itemsList.clear()
        itemsList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun getItem(position: Int): T? {
        if (position >= 0 && position < itemsList.size)
            return itemsList[position]
        else return null
    }

    fun getItemsList(): List<T> {
        return itemsList
    }

    fun updateItemAt(index: Int, item: T) {
        if (index in 0 until itemCount) {
            itemsList[index] = item
            notifyItemChanged(index)
        }
    }
}
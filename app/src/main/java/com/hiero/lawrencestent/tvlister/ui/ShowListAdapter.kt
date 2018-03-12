package com.hiero.lawrencestent.tvlister.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by lawrencestent on 2018/03/12.
 */
class ShowListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ShowListViewHolder(ShowListViewHolder.createView(parent!!))
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

    }
}
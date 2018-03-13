package com.hiero.lawrencestent.tvlister.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hiero.lawrencestent.tvlister.model.ShowModel

/**
 * Created by lawrencestent on 2018/03/12.
 */
class ShowListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var tvShows: List<ShowModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ShowListViewHolder(ShowListViewHolder.createView(parent!!))
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val newHolder = holder as ShowListViewHolder

        newHolder.updateTvShowDetail(tvShows[position])
    }

    fun updateTvShows(newTvShows: List<ShowModel>){
        this.tvShows = newTvShows
        notifyDataSetChanged()
    }


}
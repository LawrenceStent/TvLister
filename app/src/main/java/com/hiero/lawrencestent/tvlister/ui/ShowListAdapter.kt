package com.hiero.lawrencestent.tvlister.ui

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.hiero.lawrencestent.tvlister.model.ShowModel
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.viewholder_tv_show.view.*

/**
 * Created by lawrencestent on 2018/03/12.
 */
class ShowListAdapter(var tvShowDetailPublisher : PublishSubject<ShowModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val TAG = ShowListAdapter::class.java.simpleName
    }

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

        newHolder.itemView.show_card.setOnClickListener { view ->
            Log.d(TAG, "GETS HERE")
            tvShowDetailPublisher.onNext(tvShows[position])
        }
    }

    fun updateTvShows(newTvShows: List<ShowModel>){
        this.tvShows = newTvShows
        notifyDataSetChanged()
    }


}
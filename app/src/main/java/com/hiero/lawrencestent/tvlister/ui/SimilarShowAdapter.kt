package com.hiero.lawrencestent.tvlister.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hiero.lawrencestent.tvlister.model.ShowModel

/**
 * Created by lawrencestent on 2018/03/14.
 */
class SimilarShowAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var similarShowList: MutableList<ShowModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return SimilarShowViewHolder(SimilarShowViewHolder.createView(parent!!))
    }

    override fun getItemCount(): Int {
        return similarShowList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var newHolder = holder as SimilarShowViewHolder

        newHolder.updateShowDetail(similarShowList[position])
    }

    fun updateSimilarShowList(similarShows: List<ShowModel>){
        this.similarShowList = similarShows.toMutableList()
        notifyDataSetChanged()
    }
}
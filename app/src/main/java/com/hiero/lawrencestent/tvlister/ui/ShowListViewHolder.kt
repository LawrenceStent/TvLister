package com.hiero.lawrencestent.tvlister.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hiero.lawrencestent.tvlister.R

/**
 * Created by lawrencestent on 2018/03/12.
 */
class ShowListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    companion object {
        fun createView(parent: ViewGroup): View {
            return LayoutInflater.from(parent.context).inflate(R.layout.viewholder_tv_show, parent, false)
        }
    }


}
package com.hiero.lawrencestent.tvlister.ui.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hiero.lawrencestent.tvlister.R
import com.hiero.lawrencestent.tvlister.model.ShowModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewholder_similar_show.view.*

/**
 * Created by lawrencestent on 2018/03/14.
 */
class SimilarShowViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    companion object {
        fun createView(parent: ViewGroup): View{
            return LayoutInflater.from(parent.context).inflate(R.layout.viewholder_similar_show, parent, false)
        }
    }

    fun updateShowDetail(show: ShowModel){
        itemView.title_similar_show.text = show.original_name

        Picasso.with(itemView.context)
                .load(itemView.resources.getString(R.string.image_base_url) + show.poster_path)
                .fit()
                .into(itemView.poster_similar_show)

    }
}
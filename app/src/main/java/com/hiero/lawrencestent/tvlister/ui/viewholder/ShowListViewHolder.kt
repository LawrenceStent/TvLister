package com.hiero.lawrencestent.tvlister.ui.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hiero.lawrencestent.tvlister.R
import com.hiero.lawrencestent.tvlister.model.ShowModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewholder_tv_show.view.*

/**
 * Created by lawrencestent on 2018/03/12.
 */
class ShowListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    companion object {
        fun createView(parent: ViewGroup): View {
            return LayoutInflater.from(parent.context).inflate(R.layout.viewholder_tv_show, parent, false)
        }
    }

    fun updateTvShowDetail(tvShow: ShowModel){
        itemView.show_title.text = tvShow.original_name
        itemView.show_rating.text = tvShow.vote_average.toString()

//        val postPath = tvShow.poster_path?.replace("/","")
        var imageUrl = itemView.resources.getString(R.string.image_base_url)+ tvShow.poster_path

        Picasso.with(itemView.context)
                .load(imageUrl)
                .fit()
                .into(itemView.show_image)

    }


}
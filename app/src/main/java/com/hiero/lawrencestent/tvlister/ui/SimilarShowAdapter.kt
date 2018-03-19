package com.hiero.lawrencestent.tvlister.ui

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hiero.lawrencestent.tvlister.R
import com.hiero.lawrencestent.tvlister.model.ShowModel
import kotlinx.android.synthetic.main.viewholder_similar_show.view.*

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

        newHolder.itemView.similar_show_card.setOnClickListener {view ->
            openShowDetailFragment(view, similarShowList[position])
        }
    }

    fun updateSimilarShowList(similarShows: List<ShowModel>){
        this.similarShowList = similarShows.toMutableList()
        notifyDataSetChanged()
    }

    fun openShowDetailFragment(view: View, showDetail: ShowModel){
        val fragment = ShowDetailFragment.newInstance(showDetail)

        (view.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_root, fragment, ShowDetailFragment.TAG)
                .addToBackStack(null)
                .commit()
    }
}
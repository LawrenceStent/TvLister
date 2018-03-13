package com.hiero.lawrencestent.tvlister.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hiero.lawrencestent.tvlister.R
import com.hiero.lawrencestent.tvlister.model.ShowModel

/**
 * Created by lawrencestent on 2018/03/12.
 */
class ShowDetailFragment : Fragment() {

    companion object {
        val SHOW_DETAIL = "show_detail"

        fun newInstance(showDetail: ShowModel) : ShowDetailFragment{
            val fragment = ShowDetailFragment()
            var args = Bundle()
            args.putSerializable(SHOW_DETAIL, showDetail)
            fragment.arguments = args
            return fragment
        }

        val TAG = ShowDetailFragment::class.java.simpleName
    }

    var showDetailModel: ShowModel? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_show_detail, container, false)

        Log.d(TAG, "Opens fragment")
        var args = arguments
        showDetailModel = args.getSerializable(SHOW_DETAIL) as ShowModel

        val title = view?.findViewById<TextView>(R.id.show_detail_title)
        val overview = view?.findViewById<TextView>(R.id.show_detail_overview)
        val rating = view?.findViewById<TextView>(R.id.show_detail_rating)

        title?.text = showDetailModel?.original_name
        overview?.text = showDetailModel?.overview
        rating?.text = showDetailModel?.vote_average.toString()

        return view

    }
}
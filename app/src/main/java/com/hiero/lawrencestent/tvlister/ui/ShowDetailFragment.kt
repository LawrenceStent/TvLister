package com.hiero.lawrencestent.tvlister.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hiero.lawrencestent.tvlister.R
import com.hiero.lawrencestent.tvlister.model.ShowModel

/**
 * Created by lawrencestent on 2018/03/12.
 */
class ShowDetailFragment : Fragment() {

    companion object {
        fun newInstance(showDetail: ShowModel) : ShowDetailFragment{
            val fragment = ShowDetailFragment()


            return fragment
        }

        val TAG = ShowDetailFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_show_detail, container, false)

        Log.d(TAG, "Opens fragment")
        return view

    }
}
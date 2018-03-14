package com.hiero.lawrencestent.tvlister.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hiero.lawrencestent.tvlister.R
import com.hiero.lawrencestent.tvlister.model.ShowModel
import com.hiero.lawrencestent.tvlister.service.TvShowService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_show_detail.*

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

    val rxSubs : CompositeDisposable by lazy {
        CompositeDisposable()
    }

    var showDetailModel: ShowModel? = null
    var tvShowService: TvShowService? = null
    var similarShowAdapter : SimilarShowAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_show_detail, container, false)

        Log.d(TAG, "Opens fragment")
        var args = arguments
        showDetailModel = args.getSerializable(SHOW_DETAIL) as ShowModel

        tvShowService = TvShowService(resources)

        val title = view?.findViewById<TextView>(R.id.show_detail_title)
        val overview = view?.findViewById<TextView>(R.id.show_detail_overview)
        val rating = view?.findViewById<TextView>(R.id.show_detail_rating)
        val similarShowList  = view?.findViewById<RecyclerView>(R.id.list_similar_shows)

        title?.text = showDetailModel?.original_name
        overview?.text = showDetailModel?.overview
        rating?.text = showDetailModel?.vote_average.toString()


        getSimilarTvShows(tvShowService!!, showDetailModel?.id!!)

        initSimilarAdapter(view?.context, similarShowList!!)

        return view

    }

    fun getSimilarTvShows(tvShowService: TvShowService, id: Int){
        rxSubs.add(tvShowService.getSimilarShows(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ showRespose ->
                    similarShowAdapter?.updateSimilarShowList(showRespose.results)
                }, { t: Throwable ->

                }))
    }

    fun initSimilarAdapter(context: Context?, showList: RecyclerView){
        showList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        similarShowAdapter = SimilarShowAdapter()
        showList.adapter = similarShowAdapter
    }
}
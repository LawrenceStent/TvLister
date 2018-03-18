package com.hiero.lawrencestent.tvlister.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hiero.lawrencestent.tvlister.R
import com.hiero.lawrencestent.tvlister.model.ShowModel
import com.hiero.lawrencestent.tvlister.service.TvShowService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import android.widget.*
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target


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
    var getShowProgress: ProgressBar? = null
    var similarShowList: RecyclerView? = null

    var title : TextView? = null
    var overview: TextView? = null
    var rating: TextView? = null
    var heroRootLayout: FrameLayout? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_show_detail, container, false)

        var args = arguments
        showDetailModel = args.getSerializable(SHOW_DETAIL) as ShowModel
        tvShowService = TvShowService(resources)


        initToolbar(view!!, showDetailModel!!)

        initViews(view, showDetailModel!!)

        initSimilarAdapter(view.context, similarShowList!!)

        initHeroImage(view, showDetailModel!!)

        getSimilarTvShows(tvShowService!!, showDetailModel?.id!!)

        return view

    }

    fun initToolbar(view: View, showModel: ShowModel){
        (view.context as AppCompatActivity).setSupportActionBar(view.findViewById<Toolbar>(R.id.toolbar_detail))
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_detail)
        toolbar.setTitle(showModel.original_name)
        toolbar.setNavigationIcon(view.context.resources.getDrawable(R.drawable.ic_arrow_back_white_24dp))

        toolbar.setNavigationOnClickListener {
            fragmentManager.popBackStack()
        }
    }

    fun initViews(view: View, showDetailModel: ShowModel) {
//        title = view.findViewById<TextView>(R.id.show_detail_title)
        overview = view.findViewById<TextView>(R.id.show_detail_overview)
        rating = view.findViewById<TextView>(R.id.show_detail_rating)
        similarShowList = view.findViewById<RecyclerView>(R.id.list_similar_shows)


        similarShowList?.visibility = View.GONE

        getShowProgress = view.findViewById<ProgressBar>(R.id.similar_progress)
        getShowProgress?.visibility = View.VISIBLE

//        title?.text = showDetailModel.original_name
        overview?.text = showDetailModel.overview
        rating?.text = showDetailModel.vote_average.toString()
//
        overview?.background = view.context.getDrawable(R.color.white)
        overview?.alpha = 0.8f
    }

    fun initHeroImage(view: View, show: ShowModel){
        heroRootLayout = view.findViewById(R.id.big_hero_root) as FrameLayout
        heroRootLayout?.alpha = 0.95f

        Picasso.with(view.context)
                .load(view.resources?.getString(R.string.image_base_url) + show.poster_path)
                .into(object : Target {

                            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                            }

                            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                                heroRootLayout!!.background = BitmapDrawable(bitmap)
                            }

                            override fun onBitmapFailed(errorDrawable: Drawable?) {
                                Log.e(TAG, "Hero Background Failed")
                            }
                })
    }

    fun getSimilarTvShows(tvShowService: TvShowService, id: Int){
        rxSubs.add(tvShowService.getSimilarShows(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ showRespose ->
                    similarShowAdapter?.updateSimilarShowList(showRespose.results)
                    getShowProgress?.visibility = View.GONE
                    similarShowList?.visibility = View.VISIBLE
                }, { t: Throwable ->

                }))
    }

    fun initSimilarAdapter(context: Context?, showList: RecyclerView){
        showList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        similarShowAdapter = SimilarShowAdapter()
        showList.adapter = similarShowAdapter
    }
}
package com.hiero.lawrencestent.tvlister.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.hiero.lawrencestent.tvlister.R
import com.hiero.lawrencestent.tvlister.model.ShowModel
import com.hiero.lawrencestent.tvlister.service.TvShowService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    var tvShowService: TvShowService? = null

    var showListAdapter: ShowListAdapter? = null

    protected val rxSubs : CompositeDisposable by lazy {
        CompositeDisposable()
    }

    val tvShowPublisher : PublishSubject<ShowModel> by lazy {
        PublishSubject.create<ShowModel>()
    }

    val tvShowDetailObservable: Observable<ShowModel>
        get() = tvShowPublisher.hide()

    val onListEndPublisher: PublishSubject<Boolean> by lazy {
        PublishSubject.create<Boolean>()
    }

    val onListEndObserver : Observable<Boolean>
        get() = onListEndPublisher.hide()

    var pageCount: Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvShowService = TvShowService(resources)


        initAdapter()

        getTvShowList(pageCount)

        initListListeners()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "GOODBye")
    }

    fun getTvShowList(pageNumber: Int?){
        rxSubs.add(tvShowService?.getTvShowList(pageNumber!!)!!
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({tvList ->
                    Log.d(TAG, "Response: $tvList")
                    showListAdapter?.updateTvShows(sortListForRating(tvList.results))

                },{ t : Throwable->
                    Log.e(TAG, "Error with request:", t)
                }))
    }

    fun initAdapter(){
        list_tv_shows.layoutManager = LinearLayoutManager(this)
        showListAdapter = ShowListAdapter(tvShowPublisher, onListEndPublisher)
        list_tv_shows.adapter = showListAdapter
    }

    fun initListListeners(){
        rxSubs.add(tvShowDetailObservable.subscribe({ showModel ->
            openShowDetailFragment(showModel)
        }))

        rxSubs.add(onListEndObserver.subscribe({listEnd ->
            pageCount = pageCount.inc()
            getTvShowList(pageCount)

        }))
    }

    fun sortListForRating(tvShows: List<ShowModel>) : List<ShowModel>{
        var sortedShows : List<ShowModel> = tvShows.sortedWith(compareByDescending({it.vote_average}))
        return  sortedShows
    }

    fun openShowDetailFragment(showDetail: ShowModel){
        val fragment = ShowDetailFragment.newInstance(showDetail)

        supportFragmentManager.beginTransaction()
                .replace(R.id.main_root, fragment, ShowDetailFragment.TAG)
                .addToBackStack(null)
                .commit()
    }
}

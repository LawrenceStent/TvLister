package com.hiero.lawrencestent.tvlister.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
        val LOG_TAG = MainActivity::class.java.simpleName
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
    }

    fun getTvShowList(pageNumber: Int?){
        rxSubs.add(tvShowService?.getTvShowList(pageNumber!!)!!
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({tvList ->
                    Log.d(LOG_TAG, "Response: $tvList")
                    showListAdapter?.updateTvShows(tvList.results)

                },{ t : Throwable->
                    Log.e(LOG_TAG, "Error with request:", t)
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

    fun openShowDetailFragment(showDetail: ShowModel){
        val fragment = ShowDetailFragment.newInstance(showDetail)

        supportFragmentManager.beginTransaction()
                .replace(R.id.main_root, fragment, ShowDetailFragment.TAG)
                .addToBackStack(null)
                .commit()
    }
}

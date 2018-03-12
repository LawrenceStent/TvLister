package com.hiero.lawrencestent.tvlister.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.hiero.lawrencestent.tvlister.R
import com.hiero.lawrencestent.tvlister.service.TvShowService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val LOG_TAG = MainActivity::class.java.simpleName
    }

    var recyclerView: RecyclerView? = null
    var tvShowService: TvShowService? = null

    protected val rxSubs : CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvShowService = TvShowService(resources)
        list_tv_shows.adapter = ShowListAdapter()


        rxSubs.add(tvShowService?.getTvShowList()!!
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({tvList ->
                    Log.d(LOG_TAG, "Response: $tvList")
                },{ t : Throwable->
                    Log.e(LOG_TAG, "Error with request:", t)
                }))
    }
}

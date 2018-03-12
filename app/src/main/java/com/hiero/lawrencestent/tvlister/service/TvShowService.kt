package com.hiero.lawrencestent.tvlister.service

import android.content.res.Resources
import com.hiero.lawrencestent.tvlister.R
import com.hiero.lawrencestent.tvlister.TvShowApi
import com.hiero.lawrencestent.tvlister.model.ShowModel
import com.squareup.moshi.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by lawrencestent on 2018/03/12.
 */
class TvShowService(private val resources: Resources) {



    private val restAdapter : Retrofit by lazy {
        val okClient = OkHttpClient.Builder()
                .build()

        Retrofit.Builder()
                .baseUrl(resources.getString(R.string.base_url))
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okClient)
                .build()


    }

    private val tvShowApi by lazy {
        restAdapter.create(TvShowApi::class.java)
    }

    fun getTvShowList() : List<ShowModel>{
        return tvShowApi.getTvShows()
    }
}
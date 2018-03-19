package com.hiero.lawrencestent.tvlister.service

import android.content.res.Resources
import com.hiero.lawrencestent.tvlister.R
import com.hiero.lawrencestent.tvlister.rest.TvShowApi
import com.hiero.lawrencestent.tvlister.model.TvShowResponse
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by lawrencestent on 2018/03/12.
 */
class TvShowService(private val resources: Resources) {

    private val restAdapter : Retrofit by lazy {
        val okClient = OkHttpClient.Builder()
                .addInterceptor(Interceptor {chain ->

                    val baseRequest = chain.request()
                    val baseUrl = baseRequest.url()

                    val url : HttpUrl = baseUrl.newBuilder()
                            .addQueryParameter("api_key", resources.getString(R.string.tmdb_auth_key))
                            .build()

                    val request = Request.Builder()
                            .url(url)
                            .build()

                    chain.proceed(request)
                }).build()

        Retrofit.Builder()
                .baseUrl(resources.getString(R.string.base_url))
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okClient)
                .build()


    }

    private var searchResults: MutableList<String>? = mutableListOf()

    private val tvShowApi by lazy {
        restAdapter.create(TvShowApi::class.java)
    }

    fun getTvShowList(pageNum: Int) : Single<TvShowResponse>{
        return tvShowApi.getTvShows(pageNum)
    }

    fun getSimilarShows(id: Int) : Single<TvShowResponse>{
        return tvShowApi.getSimilarShows(id)
    }

    fun updateSearchResults(tvShows: List<String>){
        this.searchResults?.addAll(tvShows)
    }

    fun getSearchResults(): List<String>{
        return searchResults!!
    }
}
package com.hiero.lawrencestent.tvlister

import com.hiero.lawrencestent.tvlister.model.ShowModel
import com.hiero.lawrencestent.tvlister.model.TvShowResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by lawrencestent on 2018/03/12.
 */
interface TvShowApi {

    @GET("tv/popular") //to add page variable for pagination
    fun getTvShows(@Query("page") pageNumber: Int) : Single<TvShowResponse>

}
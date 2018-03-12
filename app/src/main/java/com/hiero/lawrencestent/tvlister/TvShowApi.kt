package com.hiero.lawrencestent.tvlister

import com.hiero.lawrencestent.tvlister.model.ShowModel
import retrofit2.http.GET

/**
 * Created by lawrencestent on 2018/03/12.
 */
interface TvShowApi {

    @GET("/tv/popular")
    fun getTvShows() : List<ShowModel>

}
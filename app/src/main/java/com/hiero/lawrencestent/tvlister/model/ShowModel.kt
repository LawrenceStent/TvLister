package com.hiero.lawrencestent.tvlister.model

import java.io.Serializable

/**
 * Created by lawrencestent on 2018/03/12.
 */
class ShowModel(val id : Int? = 0,
                val original_name: String? = "",
                val vote_average: Double = 0.0,
                val poster_path : String? = "",
                val overview: String? = "") : Serializable{
}
package com.brisbane.spacex.network

import io.reactivex.Observable
import com.brisbane.spacex.model.Launch
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface LaunchApi {

    /**
     * Get the list of the launches from the API
     */
    @GET("/v3/launches")
    fun getLaunches(@Query("sort") sort: String?, @QueryMap filterOptions: Map<String, Boolean>): Observable<List<Launch>>

    @GET("/v3/launches")
    fun getLaunchDetails(@Query("flight_number") flightNumber: Int): Observable<List<Launch>>
}

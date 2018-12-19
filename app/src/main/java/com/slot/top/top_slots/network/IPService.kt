package com.slot.top.top_slots.network

import io.reactivex.Observable
import retrofit2.http.GET


interface IPService {

    @GET("/json")
    fun getIpAddress() :Observable<IPModel>
}


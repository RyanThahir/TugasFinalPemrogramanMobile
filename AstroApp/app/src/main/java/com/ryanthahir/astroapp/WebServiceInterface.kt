package com.ryanthahir.astroapp

import retrofit2.http.GET
import rx.Observable

interface WebServiceInterface {
    @GET("random/")
    fun getSampleData(): Observable<SampleGetModel>
}
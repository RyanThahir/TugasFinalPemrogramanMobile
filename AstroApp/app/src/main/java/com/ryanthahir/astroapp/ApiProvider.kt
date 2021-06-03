package com.ryanthahir.astroapp

import android.util.Log
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ApiProvider {
    private val TAG = "ApiProvider"
    private val mApiServiceNetwork = ApiServiceNetwork.getInstance()
    fun callApi(apiResult: ApiResult) {
        try {
            mApiServiceNetwork.getNetworkService(Constant.API_ENDPOINT)
                .getSampleData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<SampleGetModel>() {
                    override fun onCompleted() {
                        //Do nothing for now
                    }
                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError" + Log.getStackTraceString(e))
                        apiResult.onAPIFail()
                    }
                    override fun onNext(sampleGetModel: SampleGetModel) {
                        Log.i(TAG, "Operation performed successfully")
                        apiResult.onModel(sampleGetModel)
                    }
                })
        } catch (e: Exception) {
            Log.e(TAG, "Exception" + Log.getStackTraceString(e))
            apiResult.onError(e)
        }

    }
}
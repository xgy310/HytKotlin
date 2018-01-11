package com.iblueroad.hyt.data.remote

import com.iblueroad.hyt.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.fastjson.FastJsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

/**
 * Created by SkyXiao on 2017/9/7.
 */
class ApiManager private constructor() {

    fun getApiStore(): ApiStore {
        if (sApiStore == null) {
            synchronized(ApiManager::class.java) {
                if (sApiStore == null) {
                    initApiStore()
                }
            }
        }
        return sApiStore
    }

    init {
        initApiStore()
    }

    private fun initApiStore() {
        sApiStore = Retrofit.Builder().baseUrl(GIRL_URL)
            .addConverterFactory(FastJsonConverterFactory.create())
            .client(configClient())
            .build()
            .create(ApiStore::class.java)
    }


    private fun configClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(TIMEOUT_SECONDS.toLong(), SECONDS).readTimeout(TIMEOUT_SECONDS.toLong(), SECONDS).writeTimeout(TIMEOUT_SECONDS.toLong(),
            SECONDS).cache(null)
        //                .retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            //设置 Debug Log 模式
            builder.addInterceptor(logging)
        }
        return builder.build()
    }

    companion object {
        private val GIRL_URL = "http://gank.io/"
        private val TIMEOUT_SECONDS = 10
        private lateinit var sApiStore: ApiStore

        val instance = ApiManager()

    }
}

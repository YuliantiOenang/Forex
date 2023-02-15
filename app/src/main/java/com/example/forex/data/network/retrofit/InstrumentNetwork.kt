package com.example.forex.data.network.retrofit

import com.example.forex.BuildConfig
import com.example.forex.data.network.InstrumentDataSource
import com.example.forex.data.network.model.InstrumentLiveDto
import com.example.forex.data.network.model.InstrumentListDto
import com.example.forex.data.network.retrofit.SecureKey.getApiKey
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton

object SecureKey {
    external fun getApiKey(): String
}

@Singleton
class InstrumentNetwork @Inject constructor(): InstrumentDataSource {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply{
        setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        )
    }

    private val networkApi = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_API_URL)
        .client(
            OkHttpClient.Builder()
                .addNetworkInterceptor {
                    val original: Request = it.request()
                    val originalHttpUrl: HttpUrl = original.url

                    val url = originalHttpUrl.newBuilder().build()

                    val requestBuilder: Request.Builder = original.newBuilder()
                        .header("apikey", getApiKey())
                        .url(url)

                    val request: Request = requestBuilder.build()
                    return@addNetworkInterceptor it.proceed(request)
                }
                .addNetworkInterceptor(loggingInterceptor)
                .build()
        )
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create<InstrumentApi>()

    override suspend fun getInstrumentLive(from: String, to: String): InstrumentLiveDto {
        return networkApi.getInstrumentLive(from, to)
    }

    override suspend fun getInstrumentList(): InstrumentListDto {
        return networkApi.getInstrumentList()
    }
}

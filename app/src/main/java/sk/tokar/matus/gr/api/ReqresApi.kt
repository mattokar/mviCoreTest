package sk.tokar.matus.gr.api

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import sk.tokar.matus.gr.api.models.user_details.UserDetail
import sk.tokar.matus.gr.api.models.user_list.UserList
import java.util.concurrent.TimeUnit

interface ReqresApi {

    @GET("/api/users")
    fun getUsers(@Query("page") page: Int, @Query("per_page") perPage: Int): Single<UserList>

    @GET("/api/users/{userId}")
    fun getUserDetails(@Path("userId") userId: Int): Single<UserDetail>

    companion object {
        fun create(): ReqresApi {
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .build()
                )
                .baseUrl("https://reqres.in")
                .build()
                .create(ReqresApi::class.java)
        }
    }

}
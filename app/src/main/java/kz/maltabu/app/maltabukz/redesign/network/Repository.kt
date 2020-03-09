package kz.maltabu.app.maltabukz.redesign.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import kz.maltabu.app.maltabukz.redesign.network.model.request.GetPostsRequest
import kz.maltabu.app.maltabukz.redesign.network.model.response.News
import kz.maltabu.app.maltabukz.redesign.network.model.response.PostCatalogResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class Repository{

    private var mainService: Service? = null

    private constructor (){
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()
                    .addHeader("isAuthorized", "false")
                    .addHeader("Content-Type", "application/json")
            chain.proceed(builder.build())
        }
                .connectTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
        val gson = GsonBuilder().setLenient().create()
        val rxAdapter = RxJava2CallAdapterFactory.create()
        mainService = Retrofit.Builder()
                .baseUrl("https://maltabu.kz")
                .client(clientBuilder.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create<Service>(Service::class.java)
    }

    private constructor (token : String){
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()
                    .addHeader("isAuthorized", "true")
                    .addHeader("token", token)
                    .addHeader("Content-Type", "application/json")
            chain.proceed(builder.build())
        }.connectTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
        val gson = GsonBuilder().setLenient().create()
        val rxAdapter = RxJava2CallAdapterFactory.create()
        mainService = Retrofit.Builder()
                .baseUrl("https://maltabu.kz")
                .client(clientBuilder.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create<Service>(Service::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance(): Repository {
            return Repository()
        }

        @JvmStatic
        fun newInstance(token : String): Repository {
            return Repository(token)
        }
    }

    fun getPosts(body: GetPostsRequest): Observable<Response<PostCatalogResponse>> {
        return mainService!!.getPosts(body)
    }

    fun getNews(lang: String, page: Int): Observable<Response<List<News>>>{
        return mainService!!.getNews(lang, page)
    }

    fun getNewsByNumber(number: Int): Observable<Response<News>>{
        return mainService!!.getNewsByLang(number)
    }
}
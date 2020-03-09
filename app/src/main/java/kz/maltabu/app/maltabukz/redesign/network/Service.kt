package kz.maltabu.app.maltabukz.redesign.network

import io.reactivex.Observable
import kz.maltabu.app.maltabukz.redesign.network.model.request.GetPostsRequest
import kz.maltabu.app.maltabukz.redesign.network.model.response.News
import kz.maltabu.app.maltabukz.redesign.network.model.response.PostCatalogResponse
import retrofit2.Response
import retrofit2.http.*

interface Service {
    @POST("v1/api/clients/posts")
    fun getPosts(@Body body: GetPostsRequest): Observable<Response<PostCatalogResponse>>

    @GET("v1/api/clients/news/{localize}/{page}")
    fun getNews(@Path("localize") lang: String, @Path("page") page:Int): Observable<Response<List<News>>>

    @GET("v1/api/clients/news/{number}")
    fun getNewsByLang(@Path("number") number:Int): Observable<Response<News>>
}
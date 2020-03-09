package kz.maltabu.app.maltabukz.redesign.network.model

import io.reactivex.annotations.NonNull
import io.reactivex.annotations.Nullable

import android.util.Pair
import kz.maltabu.app.maltabukz.redesign.network.model.response.Post
import kz.maltabu.app.maltabukz.redesign.utils.Status
import retrofit2.Response
import kotlin.collections.ArrayList

class ApiResponse private constructor(
        val status: Status, @param:Nullable @field:Nullable
        val data: Response<*>?, @param:Nullable @field:Nullable
        val error: Response<*>?, @param:Nullable @field:Nullable
        val throwabl: Throwable?
) {
    companion object {

        fun loading(): ApiResponse {
            return ApiResponse(Status.LOADING, null, null, null)
        }

        fun success(@NonNull data: Response<*>): ApiResponse {
            return ApiResponse(Status.SUCCESS, data, null, null)
        }

        fun error(@NonNull error: Response<*>): ApiResponse {
            return ApiResponse(Status.ERROR, null, error, null)
        }

        fun throwable(@NonNull throwabl: Throwable): ApiResponse {
            return ApiResponse(Status.THROWABLE, null, null, throwabl)
        }
    }

}
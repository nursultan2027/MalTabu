package kz.maltabu.app.maltabukz.redesign.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Observable

import android.util.Pair
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kz.maltabu.app.maltabukz.redesign.network.Repository
import kz.maltabu.app.maltabukz.redesign.network.model.ApiResponse
import kz.maltabu.app.maltabukz.redesign.network.model.request.GetPostsRequest
import kz.maltabu.app.maltabukz.redesign.network.model.response.Post
import java.util.ArrayList

class CatalogFragmentViewModel() : ViewModel(){
    private val disposable = CompositeDisposable()
    private val response = MutableLiveData<ApiResponse>()

    fun mainResponse(): MutableLiveData<ApiResponse> {
        return response
    }

    fun getCategory(body: GetPostsRequest){
        disposable.add(Repository.newInstance().getPosts(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { response.value=ApiResponse.loading()}
                .subscribe {
                    if(it.code()==200){
                        response.value= ApiResponse.success(it)
                    } else {
                        response.value= ApiResponse.error(it)
                    }
                })
    }

    class ViewModelFactory(): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CatalogFragmentViewModel() as T
        }
    }
}
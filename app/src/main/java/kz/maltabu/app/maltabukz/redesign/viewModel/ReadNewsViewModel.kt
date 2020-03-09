package kz.maltabu.app.maltabukz.redesign.viewModel

import android.util.Log
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

class ReadNewsViewModel() : ViewModel(){
    private val disposable = CompositeDisposable()

    fun getNewsCount(newsNumber: Int){
        disposable.add(Repository.newInstance().getNewsByNumber(newsNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(it.code()==200){
                        Log.d("TAG", "added")
                    } else {
                        Log.d("TAG", "not added")
                    }
                })
    }

    class ViewModelFactory(): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ReadNewsViewModel() as T
        }
    }
}
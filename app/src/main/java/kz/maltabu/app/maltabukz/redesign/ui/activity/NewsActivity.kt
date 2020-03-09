package kz.maltabu.app.maltabukz.redesign.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kz.maltabu.app.maltabukz.R
import kz.maltabu.app.maltabukz.adapters.NativeTemplateAdapter
import kz.maltabu.app.maltabukz.redesign.network.model.ApiResponse
import kz.maltabu.app.maltabukz.redesign.network.model.response.News
import kz.maltabu.app.maltabukz.redesign.utils.Status
import kz.maltabu.app.maltabukz.redesign.viewModel.NewsViewModel
import kotlin.collections.ArrayList
import android.util.Pair
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yandex.mobile.ads.AdRequestError
import com.yandex.mobile.ads.nativeads.NativeAdLoader
import com.yandex.mobile.ads.nativeads.NativeAppInstallAd
import com.yandex.mobile.ads.nativeads.NativeContentAd
import com.yandex.mobile.ads.nativeads.NativeImageAd
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.activity_news.button
import kotlinx.android.synthetic.main.activity_news.progress_lay
import kotlinx.android.synthetic.main.category_fragment_redesign.prodss
import kz.maltabu.app.maltabukz.activities.MainActivity2
import kz.maltabu.app.maltabukz.helpers.CustomAnimator
import kz.maltabu.app.maltabukz.helpers.EndlessListener
import kz.maltabu.app.maltabukz.helpers.Maltabu
import kz.maltabu.app.maltabukz.helpers.yandex.Holder
import kz.maltabu.app.maltabukz.helpers.yandex.YandexAdHelper

class NewsActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NativeTemplateAdapter
    private var mData = ArrayList<Pair<Int, Any>>()
    private var pageNumber=1
    private var canLoad = true
    private lateinit var yandexHelper: YandexAdHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        yandexHelper= YandexAdHelper(this)
        yandexHelper.createNativeAdLoader(nativeAdLoadListener)
        viewModel = ViewModelProviders.of(this, NewsViewModel.ViewModelFactory()).get(
                NewsViewModel::class.java)
        getNewsByLang()
        adapter= NativeTemplateAdapter(this)
        news_list.adapter=adapter
        news_list.layoutManager= LinearLayoutManager(this)
        viewModel.mainResponse().observe(this, Observer {
            consumeResponse(it)
        })
        setListeners()
    }

    private fun getNewsByLang() {
        if(Maltabu.lang=="ru")
            viewModel.getNews("ru", pageNumber)
        else
            viewModel.getNews("kz", pageNumber)
    }

    private fun setListeners() {
        arrr.setOnClickListener(View.OnClickListener {
            CustomAnimator.animateViewBound(it)
            onBackPressed()
        })
        news_list.addOnScrollListener(
                object : EndlessListener(news_list.layoutManager as LinearLayoutManager){
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView){
                        if(canLoad) {
                            pageNumber++
                            getNewsByLang()
                        }
                    }
                }
        )
    }

    private fun consumeResponse(response: ApiResponse) {
        when (response.status) {
            Status.LOADING -> { showLoader() }
            Status.SUCCESS -> {
                cancelLoader()
                Log.d("ATgg", (response.data!!.body() as List<News>).size.toString())
                renderResponse(response.data!!.body() as List<News>)
            }
            Status.ERROR -> {
                cancelLoader()
                Log.d("ATgg", "error")
            }
            Status.THROWABLE -> {
                cancelLoader()
                Log.d("ATgg", "notOk")
            }
        }
    }

    private fun showLoader(){
        if(pageNumber==1)
            progress_lay.visibility=View.VISIBLE
        else {
            button.isIndeterminate = true
            button.visibility = View.VISIBLE
        }
    }

    private fun cancelLoader(){
        if(pageNumber==1)
            progress_lay.visibility=View.GONE
        else
            button.visibility=View.INVISIBLE
    }

    private fun renderResponse(responseList: List<News>) {
        if(responseList.size<10)
            canLoad=false
        for (i in responseList.indices){
            mData.add(Pair(2,responseList[i]))
        }
        loadAd()
        adapter.setData(mData)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity2::class.java))
        finish()
    }

    private fun loadAd(){
        yandexHelper.loadAd()
    }

    override fun onStart() {
        super.onStart()
        mData.clear()
        pageNumber=1
        getNewsByLang()
    }

    private val nativeAdLoadListener = object : NativeAdLoader.OnImageAdLoadListener {
        override fun onAppInstallAdLoaded(nativeAppInstallAd: NativeAppInstallAd) {
            fillData(Pair(Holder.BlockContentProvider.NATIVE_BANNER, nativeAppInstallAd))
        }

        override fun onContentAdLoaded(nativeContentAd: NativeContentAd) {
            fillData(Pair(Holder.BlockContentProvider.NATIVE_BANNER, nativeContentAd))
        }

        override fun onImageAdLoaded(nativeImageAd: NativeImageAd) {
            fillData(Pair(Holder.BlockContentProvider.NATIVE_BANNER, nativeImageAd))
        }

        override fun onAdFailedToLoad(error: AdRequestError) {
            Log.d("SAMPLE_TAG", error.description)
        }

        private fun fillData(nativeAd: Pair<Int, Any>) {
            mData.add(nativeAd)
        }
    }
}
package kz.maltabu.app.maltabukz.redesign.ui.fragment

import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yandex.mobile.ads.AdRequestError
import com.yandex.mobile.ads.nativeads.NativeAdLoader
import com.yandex.mobile.ads.nativeads.NativeAppInstallAd
import com.yandex.mobile.ads.nativeads.NativeContentAd
import com.yandex.mobile.ads.nativeads.NativeImageAd
import kotlinx.android.synthetic.main.category_fragment.prodss
import kotlinx.android.synthetic.main.category_fragment_redesign.*
import kz.maltabu.app.maltabukz.R
import kz.maltabu.app.maltabukz.adapters.NativeTemplateAdapter
import kz.maltabu.app.maltabukz.helpers.EndlessListener
import kz.maltabu.app.maltabukz.helpers.FileHelper
import kz.maltabu.app.maltabukz.helpers.Maltabu
import kz.maltabu.app.maltabukz.helpers.yandex.Holder
import kz.maltabu.app.maltabukz.helpers.yandex.YandexAdHelper
import kz.maltabu.app.maltabukz.redesign.network.model.ApiResponse
import kz.maltabu.app.maltabukz.redesign.network.model.request.GetPostsRequest
import kz.maltabu.app.maltabukz.redesign.network.model.response.Post
import kz.maltabu.app.maltabukz.redesign.network.model.response.PostCatalogResponse
import kz.maltabu.app.maltabukz.redesign.utils.Status
import kz.maltabu.app.maltabukz.redesign.viewModel.CatalogFragmentViewModel
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class CatalogFragmentRedesign : Fragment() {

    private lateinit var viewModel: CatalogFragmentViewModel
    private lateinit var fileHelper: FileHelper
    private lateinit var catalog: String
    private lateinit var adapter: NativeTemplateAdapter
    private lateinit var yandexHelper: YandexAdHelper
    private var isCatalog=false
    private var pageNumber=1
    private var adIndex=5
    private var promosAdded=false
    private var mData = ArrayList<Pair<Int, Any>>()

    companion object{
        @JvmStatic
        fun newInstance(): CatalogFragmentRedesign{
            return CatalogFragmentRedesign()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        viewModel = ViewModelProviders.of(this, CatalogFragmentViewModel.ViewModelFactory()).get(
                CatalogFragmentViewModel::class.java)
        fileHelper = FileHelper(activity)
        isCatalog = bundle!!.getBoolean("isCatalog")
        catalog = bundle.getString("catalog")
        pageNumber = 1
        promosAdded = false
        adapter= NativeTemplateAdapter(activity)
        yandexHelper= YandexAdHelper(activity!!)
        yandexHelper.createNativeAdLoader(nativeAdLoadListener)
        viewModel.mainResponse().observe(this, Observer {
            consumeResponse(it)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        return inflater.inflate(R.layout.category_fragment_redesign, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCategory(buildBodyForRequest())
        prodss.adapter=adapter
        prodss.layoutManager=LinearLayoutManager(activity)
        adapter.setData(mData)
        setListeners()
    }

    private fun consumeResponse(response: ApiResponse){
        when (response.status) {
            Status.LOADING -> showLoader()
            Status.SUCCESS -> {
                cancelLoader()
                catalog_swipe.isRefreshing=false
                renderResponse(response.data as Response<PostCatalogResponse>)
            }
            Status.ERROR -> {
                cancelLoader()
                noResult()
            }
            Status.THROWABLE -> {
                cancelLoader()
                noResult()
            }
        }
    }

    private fun renderResponse(response:  Response<PostCatalogResponse>){
        if (pageNumber==1)
            addToPair(response.body()!!.promos, true)
        addToPair(response.body()!!.posts, false)
        if(mData.isNotEmpty()){
            haveResult()
            adapter.notifyDataSetChanged()
            adapter.setData(mData)
        }
        else
            noResult()
    }

    private fun addToPair(list: List<Post>, promo: Boolean){
        if(list.isNotEmpty())
            for (i in list.indices) {
                list[i].isPromoted=promo
                mData.add(Pair(0, list[i]))
                if(i%5==4){
                    loadAd()
                }
            }
    }

    private fun setListeners(){
        catalog_swipe.setOnRefreshListener {
            prodss.visibility=View.GONE
            resetData()
            viewModel.getCategory(buildBodyForRequest())
        }
        prodss.addOnScrollListener(
            object : EndlessListener(prodss.layoutManager as LinearLayoutManager){
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView){
                    pageNumber++
                    viewModel.getCategory(buildBodyForRequest())
                }
            }
        )
    }

    private fun resetData() {
        mData.clear()
        adIndex=5
        pageNumber=1
    }

    private fun noResult(){
        no_ads_image.visibility=View.VISIBLE
        no_ads_text.visibility=View.VISIBLE
        prodss.visibility=View.GONE
    }

    private fun haveResult(){
        no_ads_image.visibility=View.GONE
        no_ads_text.visibility=View.GONE
        prodss.visibility=View.VISIBLE
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

    private fun buildBodyForRequest(): GetPostsRequest{

        val body = GetPostsRequest(pageNumber, Maltabu.byTime, Maltabu.increment, true)
        if (this.isCatalog) {
            body.catalogID=catalog
        } else {
            body.categoryID=catalog
        }
        if (Maltabu.filterModel != null) {
            val filter = Maltabu.filterModel
            if (filter.regId != null) {
                body.regionID=filter.regId
            }
            if (filter.cityId != null) {
                body.cityID=filter.cityId
            }
            if (filter.price1 != null) {
                body.fromPrice=Integer.parseInt(filter.price1)
            }
            if (filter.price2 != null) {
                body.toPrice=Integer.parseInt(filter.price2)
            }
            body.onlyImages=filter.isWithPhoto
            body.onlyExchange=filter.isBarter
            body.onlyEmergency=filter.isBargain
        }
        return body
    }

    private fun loadAd(){
        yandexHelper.loadAd()
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

            mData.add(adIndex,nativeAd)
            adIndex+=6
            adapter.notifyDataSetChanged()
            adapter.setData(mData)
        }
    }
}
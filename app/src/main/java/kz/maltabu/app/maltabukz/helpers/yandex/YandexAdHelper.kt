package kz.maltabu.app.maltabukz.helpers.yandex

import android.content.Context
import com.yandex.mobile.ads.AdRequest
import com.yandex.mobile.ads.nativeads.NativeAdLoader
import com.yandex.mobile.ads.nativeads.NativeAdLoaderConfiguration

class YandexAdHelper(val context:Context) {
    private lateinit var mNativeAdLoader: NativeAdLoader

    fun loadAd() {
        mNativeAdLoader.loadAd(AdRequest.builder().build())
    }

    fun createNativeAdLoader(nativeAdLoadListener: NativeAdLoader.OnImageAdLoadListener) {
        val adLoaderConfiguration = NativeAdLoaderConfiguration.Builder("R-M-441970-2", false)
                .setImageSizes(NativeAdLoaderConfiguration.NATIVE_IMAGE_SIZE_SMALL).build()
        mNativeAdLoader = NativeAdLoader(context, adLoaderConfiguration)
        mNativeAdLoader.setNativeAdLoadListener(nativeAdLoadListener)
    }
}
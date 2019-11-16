package kz.maltabu.app.maltabukz.fragments;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.yandex.mobile.ads.AdRequest;
import com.yandex.mobile.ads.AdRequestError;
import com.yandex.mobile.ads.nativeads.NativeAdLoader;
import com.yandex.mobile.ads.nativeads.NativeAdLoaderConfiguration;
import com.yandex.mobile.ads.nativeads.NativeAppInstallAd;
import com.yandex.mobile.ads.nativeads.NativeContentAd;
import com.yandex.mobile.ads.nativeads.NativeImageAd;
import com.yandex.mobile.ads.nativeads.template.NativeBannerView;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.yandex.Holder;
import uk.co.senab.photoview.PhotoViewAttacher;


public class YandexNativeAdFragment extends Fragment {
    private NativeAdLoader mNativeAdLoader;
    private NativeBannerView mNativeBannerView;

    public YandexNativeAdFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_ad,null);
        mNativeBannerView = view.findViewById(R.id.native_template);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createNativeAdLoader();
        loadAd();
    }

    private void loadAd() {
        mNativeAdLoader.loadAd(AdRequest.builder().build());
    }

    private void createNativeAdLoader() {
        final NativeAdLoaderConfiguration adLoaderConfiguration =
                new NativeAdLoaderConfiguration.Builder("R-M-441970-1", false)
                        .setImageSizes(NativeAdLoaderConfiguration.NATIVE_IMAGE_SIZE_MEDIUM).build();
        mNativeAdLoader = new NativeAdLoader(getActivity(), adLoaderConfiguration);
        mNativeAdLoader.setNativeAdLoadListener(mNativeAdLoadListener);
    }

    private NativeAdLoader.OnImageAdLoadListener mNativeAdLoadListener = new NativeAdLoader.OnImageAdLoadListener(){
        @Override
        public void onAppInstallAdLoaded(@NonNull final NativeAppInstallAd nativeAppInstallAd) {
            nativeAppInstallAd.loadImages();
            mNativeBannerView.setAd(nativeAppInstallAd);
        }

        @Override
        public void onContentAdLoaded(@NonNull final NativeContentAd nativeContentAd) {
            nativeContentAd.loadImages();
            mNativeBannerView.setAd(nativeContentAd);
        }

        @Override
        public void onImageAdLoaded(@NonNull NativeImageAd nativeImageAd) {
            nativeImageAd.loadImages();
            mNativeBannerView.setAd(nativeImageAd);
        }

        @Override
        public void onAdFailedToLoad(@NonNull final AdRequestError error) {
            Log.d("SAMPLE_TAG", error.getDescription());
        }

    };

}
package kz.maltabu.app.maltabukz.redesign.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_read_news.*
import kz.maltabu.app.maltabukz.R
import kz.maltabu.app.maltabukz.redesign.network.model.response.News
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kz.maltabu.app.maltabukz.redesign.viewModel.ReadNewsViewModel


class ReadNewsActivity : AppCompatActivity() {

    private lateinit var viewModel: ReadNewsViewModel
    private lateinit var news: News

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(kz.maltabu.app.maltabukz.R.layout.activity_read_news)
        viewModel = ViewModelProviders.of(this, ReadNewsViewModel.ViewModelFactory()).get(
                ReadNewsViewModel::class.java)
        news = intent.getParcelableExtra<News>("news")
        Picasso.with(this).load(news.pictures.original).into(backdrop)
        toolbar.text = news.title
        desc.text=news.desc
        viewModel.getNewsCount(news.number)
        val mAdView: AdView = findViewById<View>(R.id.adView) as AdView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
}

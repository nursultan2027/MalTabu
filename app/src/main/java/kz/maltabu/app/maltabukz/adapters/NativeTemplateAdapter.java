package kz.maltabu.app.maltabukz.adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yandex.mobile.ads.nativeads.NativeGenericAd;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.activities.ShowDetails;
import kz.maltabu.app.maltabukz.helpers.CustomAnimator;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.InputValidation;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.helpers.yandex.Holder;
import kz.maltabu.app.maltabukz.helpers.yandex.NativeBlockContentHelper;
import kz.maltabu.app.maltabukz.redesign.network.model.response.News;
import kz.maltabu.app.maltabukz.redesign.network.model.response.Post;
import kz.maltabu.app.maltabukz.redesign.network.model.response.Price;
import kz.maltabu.app.maltabukz.redesign.ui.activity.NewsActivity;
import kz.maltabu.app.maltabukz.redesign.ui.activity.ReadNewsActivity;

public class NativeTemplateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final NativeBlockContentHelper mBlockContentHelper;
    private List<Pair<Integer, Object>> mData;
    private Context mContext;
    private CustomAnimator animator;
    private FileHelper fileHelper;
    private JSONObject object;

    public NativeTemplateAdapter(Context context) {
        mContext = context;
        animator = new CustomAnimator();
        mBlockContentHelper = new NativeBlockContentHelper();
        fileHelper = new FileHelper(context);
        try {
            object = fileHelper.diction();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setData(@NonNull List<Pair<Integer, Object>> dataList) {
        mData = dataList;
        mBlockContentHelper.setData(mData);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch(viewType) {
            case Holder.BlockContentProvider.DEFAULT: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
                viewHolder = new Holder.ListItemHolder(view);
                break;
            }

            case Holder.BlockContentProvider.NATIVE_BANNER: {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_native_template, parent, false);
                viewHolder = new Holder.NativeBannerViewHolder(view);
                break;
            }

            case Holder.BlockContentProvider.NEWS_ITEM:{
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
                viewHolder = new Holder.NewsItemHolder(view);
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch(viewType) {
            case Holder.BlockContentProvider.NATIVE_BANNER: {
                bindNativeBanner((Holder.NativeBannerViewHolder) holder, position);
                break;
            }
            case Holder.BlockContentProvider.DEFAULT:{
                bindItem((Holder.ListItemHolder) holder, position);
                break;
            }
            case Holder.BlockContentProvider.NEWS_ITEM:{
                bindNewsItem((Holder.NewsItemHolder) holder, position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mBlockContentHelper.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return mBlockContentHelper.getItemType(position);
    }

    private void bindNativeBanner(final Holder.NativeBannerViewHolder holder, final int position) {
        holder.nativeBannerView.setVisibility(View.GONE);
        final NativeGenericAd nativeAd = (NativeGenericAd) mData.get(position).second;

        holder.nativeBannerView.setAd(nativeAd);
        holder.nativeBannerView.setVisibility(View.VISIBLE);
        holder.nativeBannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.animateHotViewLinear(holder.itemView);
            }
        });
        nativeAd.loadImages();
    }


    private void bindNewsItem(final Holder.NewsItemHolder holder, int position) {
        final News news = (News) mData.get(position).second;
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDesc());
        Picasso.with(mContext).load(news.getPictures().getSmall()).placeholder(R.drawable.listempty).centerCrop().fit().into(holder.img);
        holder.itemView.setOnClickListener(v -> {
            Intent readIntent = new Intent(mContext, ReadNewsActivity.class);
            readIntent.putExtra("news",news);
            Pair [] opPairs = new Pair[2];
            opPairs [0] = new Pair<View, String> (holder.img,"news_img_tran");
            opPairs [1] = new Pair<View, String> (holder.description,"news_desc_tran");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((NewsActivity)mContext,opPairs);
            mContext.startActivity(readIntent, options.toBundle());
        });
        String date = new InputValidation(mContext).getDate(news.getCreatedAt());
        String dates [] = date.split(",");
        if (Maltabu.lang.equals("ru")) {
            holder.date.setText(dates[0]+ " "+dates[1]);
        } else {
            holder.date.setText(dates[0]+ " "+dates[2]);
        }
        holder.visitors.setText(String.valueOf(news.getStat().getVisitors()));
    }

    private void bindItem(final  Holder.ListItemHolder holder, final int position){
        final Post post = (Post) mData.get(position).second;
        holder.nameView.setText(post.getTitle());
        holder.visitors.setText(String.valueOf(post.getStat().getVisitors()));
        holder.nameView2.setText(getValidPrice(post.getPrice()));
        holder.commCount.setText(String.valueOf(post.getComments().size()));
        String date = new InputValidation(mContext).getDate(post.getCreatedAt());
        String dates [] = date.split(",");
        if (Maltabu.lang.equals("ru")) {
            holder.nameView3.setText(post.getCityID().getName()+", "+dates[0]+ " "+dates[1]);
        } else {
            try {
                holder.nameView3.setText(object.getString(post.getCityID().getName())
                        +", "+dates[0]+ " "+dates[2]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(post.getImg().getWeb().size()>0) {
            if(post.getImg().getWeb().get(0).getMedium().contains("http"))
                Picasso.with(mContext).load(post.getImg().getWeb().get(0).getSmall()).placeholder(R.drawable.listempty).centerCrop().fit().into(holder.img);
            else
                Picasso.with(mContext).load("https://maltabu.kz"+post.getImg().getWeb().get(0).getSmall()).placeholder(R.drawable.listempty).centerCrop().fit().into(holder.img);
            holder.photoCount.setText(String.valueOf(post.getImg().getWeb().size()));
        } else {
            Picasso.with(mContext).load(R.drawable.listempty).centerCrop().fit().into(holder.img);
            holder.photoCount.setText(String.valueOf(0));
        }
        if(post.isPromoted){
            holder.top.setVisibility(View.VISIBLE);
        } else {
            holder.top.setVisibility(View.GONE);
        }
        holder.selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            animator.animateHotViewLinear(holder.itemView);
            SecondThread thread = new SecondThread(String.valueOf(post.getNumber()));
            thread.start();
            }
        });
    }

    private String getValidPrice(Price price) {
        if(price.getKind().equals("value")){
            return String.valueOf(price.getValue())+" ₸";
        } else {
            if(price.getKind().equals("free")){
                if(Maltabu.lang.equals("ru")){
                    return  "Отдам даром";
                } else {
                    try {
                        return object.getString("Отдам даром");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "";
    }

    private void getPost(String numb){
        Intent details = new Intent(mContext, ShowDetails.class);
        details.putExtra("postNumb", numb);
        mContext.startActivity(details);
    }

    public class SecondThread extends Thread{
        String numb;
        SecondThread (String numb){
            this.numb = numb;
        }

        @Override
        public void run() {
            getPost(numb);
        }
    }
}

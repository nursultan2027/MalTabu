package kz.maltabu.app.maltabukz.helpers.yandex;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.yandex.mobile.ads.nativeads.template.NativeBannerView;

import kz.maltabu.app.maltabukz.R;

public class Holder {

    public class BlockContentProvider {
        public static final int NONE_TYPE = -1;
        public static final int DEFAULT = 0;
        public static final int NATIVE_BANNER = 1;
    }

    public static class ListItemHolder extends RecyclerView.ViewHolder {
        public TextView nameView ;
        public TextView nameView2;
        public TextView nameView3;
        public TextView photoCount;
        public TextView commCount;
        public TextView visitors;
        public ConstraintLayout selected;
        public ImageView img;
        public ImageView top;

        public ListItemHolder(View itemView) {
            super(itemView);
            selected = (ConstraintLayout) itemView.findViewById(R.id.selectedPost);
            nameView = (TextView) itemView.findViewById(R.id.textView3);
            nameView2 = (TextView) itemView.findViewById(R.id.textView4);
            nameView3 = (TextView) itemView.findViewById(R.id.textView5);
            commCount = (TextView) itemView.findViewById(R.id.textView9);
            photoCount = (TextView) itemView.findViewById(R.id.textView11);
            visitors = (TextView) itemView.findViewById(R.id.textView10);
            img = (ImageView) itemView.findViewById(R.id.imageView17);
            top = (ImageView) itemView.findViewById(R.id.topIcon);
        }
    }


    public static class NativeBannerViewHolder extends RecyclerView.ViewHolder {

        public final NativeBannerView nativeBannerView;

        public NativeBannerViewHolder(View itemView) {
            super(itemView);
            nativeBannerView = (NativeBannerView) itemView.findViewById(R.id.native_template);
        }
    }

}

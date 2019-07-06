package kz.maltabu.app.maltabukz.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import kz.maltabu.app.maltabukz.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class ImageFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    public static ImageFragment newInstance(int page, String url) {
        ImageFragment imgFragment = new ImageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putString(ARGUMENT_PAGE_NUMBER, url);
        imgFragment.setArguments(arguments);
        return imgFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_item,null);
        Bundle bundle = this.getArguments();
        if (this.getArguments() != null && getArguments().containsKey(ARGUMENT_PAGE_NUMBER)) {
            String url = bundle.getString(ARGUMENT_PAGE_NUMBER);
            ImageView card = (ImageView) view.findViewById(R.id.imgPage);
            final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.load);
            Picasso.with(getContext()).load("https://maltabu.kz/"
                    +url).centerCrop().fit().into(card, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }
                @Override
                public void onError() {

                }
            });
        }
        return view;
    }

}
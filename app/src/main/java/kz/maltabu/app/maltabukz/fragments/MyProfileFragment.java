package kz.maltabu.app.maltabukz.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.activities.AddScore;
import kz.maltabu.app.maltabukz.activities.AuthAvtivity;
import kz.maltabu.app.maltabukz.activities.ChangePassword;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;

import org.json.JSONException;
import org.json.JSONObject;


public class MyProfileFragment extends Fragment {
    private View view;
    private ConstraintLayout constraintLayout32, exit, changePass;
    private FileHelper fileHelper;
    private TextView email, number, balance;
    private Button Score;
    private JSONObject object;

    public MyProfileFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.my_profile_fragment, container, false);
        fileHelper = new FileHelper(getActivity());
        email = (TextView) view.findViewById(R.id.textView35);
        number = (TextView) view.findViewById(R.id.number);
        constraintLayout32 = view.findViewById(R.id.constraintLayout32);
        changePass = view.findViewById(R.id.constraintLayout34);
        balance = (TextView) view.findViewById(R.id.textView37);
        exit = (ConstraintLayout) view.findViewById(R.id.exit);
        Score = (Button) view.findViewById(R.id.button4);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePassword.class));
            }
        });
        Score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent payment = new Intent(getActivity(), AddScore.class);
                    payment.putExtra("numb", String.valueOf(object.getInt("number")));
                    Pair [] opPairs = new Pair[1];
                    opPairs [0] = new Pair<View, String> (number,"numberTransition");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),opPairs);
                    getActivity().startActivity(payment, options.toBundle());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maltabu.token = null;
                Maltabu.isAuth = "false";
                fileHelper.writeToken("");
                fileHelper.writeUserFile("");
                getActivity().startActivity(new Intent(getActivity(), AuthAvtivity.class));
                getActivity().finish();
            }
        });
        try {
            object = new JSONObject(fileHelper.readUserFile());
            email.setText(object.getString("mail"));
            number.setText(String.valueOf(object.getInt("number")));
            balance.setText(String.valueOf(object.getInt("balance"))+" ед.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        constraintLayout32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager host = (ViewPager) getActivity().findViewById(R.id.pager);
                host.setCurrentItem(0);
            }
        });
        return view;
    }
}
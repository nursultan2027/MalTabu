package kz.maltabu.app.maltabukz.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.adapters.TransactionAdapter;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.models.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class MyScoreFragment extends Fragment {
    private View view;
    private FileHelper fileHelper;
    private JSONObject object;
    private TextView txt;
    private ListView listView;

    public MyScoreFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.my_score_fragment, container, false);
        fileHelper = new FileHelper(getActivity());
        listView = view.findViewById(R.id.prodss);
        txt = (TextView) view.findViewById(R.id.textView50);
        categList();
        return view;
    }


    public void categList() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            object = new JSONObject(fileHelper.readPostingFile());
            Gson googleJson = new Gson();
            JSONArray resObj = object.getJSONArray("transactions");
            ArrayList transObjList = googleJson.fromJson(String.valueOf(resObj), ArrayList.class);
            for(int i=0; i<transObjList.size();i++){
                JSONObject transJsonObject = resObj.getJSONObject(i);
                String createdAt = transJsonObject.getString("createdAt");
                String kind = transJsonObject.getString("kind");
                String source = transJsonObject.getString("source");
                String value = String.valueOf(transJsonObject.getInt("value"));
                Transaction transaction = new Transaction(getDate(createdAt),kind,source,value);
                if(transJsonObject.has("postID")){

                    transaction.setPostNumber(String.valueOf(transJsonObject.getJSONObject("postID").getInt("number")));
                    transaction.setTitle(transJsonObject.getJSONObject("postID").getString("title"));
                }
                transactions.add(transaction);
            }
            Collections.reverse(transactions);
            TransactionAdapter adapter = new TransactionAdapter(getActivity(), R.layout.transaction_item,transactions);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDate(String s)    {
        String [] ss = s.split("T");
        String [] ss2 = ss[0].split("-");
        if (ss2[1].equals("01"))
        {
            ss2[1] = "Января,қаңтар";
        } else {
            if (ss2[1].equals("02"))
            {
                ss2[1] = "Февраля,ақпан";
            }
            else {
                if (ss2[1].equals("03"))
                {
                    ss2[1] = "Марта,наурыз";
                }
                else {
                    if (ss2[1].equals("04"))
                    {
                        ss2[1] = "Апреля,сәуiр";
                    } else {
                        if (ss2[1].equals("05"))
                        {
                            ss2[1] = "Мая,мамыр";
                        } else {
                            if (ss2[1].equals("06"))
                            {
                                ss2[1] = "Июня,маусым";
                            }
                            else {
                                if (ss2[1].equals("07"))
                                {
                                    ss2[1] = "Июля,шiлде";
                                } else {
                                    if (ss2[1].equals("08"))
                                    {
                                        ss2[1] = "Августа,тамыз";
                                    }
                                    else {
                                        if (ss2[1].equals("09"))
                                        {
                                            ss2[1] = "Сентября,қыркүйек";
                                        }
                                        else {
                                            if (ss2[1].equals("10"))
                                            {
                                                ss2[1] = "Октября,қазан";
                                            }
                                            else {
                                                if (ss2[1].equals("11"))
                                                {
                                                    ss2[1] = "Ноября,қараша";
                                                }
                                                else {
                                                    if (ss2[1].equals("12"))
                                                    {
                                                        ss2[1] = "Декабря,желтоқсан";
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        String time  = ss[1].substring(0,8);
        return ss2[2] +", "+ss2[1]+"uu"+time;
    }



}
package kz.maltabu.app.maltabukz.adapters;

import android.content.Context;
import android.content.Intent;

import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import kz.maltabu.app.maltabukz.R;

import kz.maltabu.app.maltabukz.activities.ShowDetails;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Transaction;

import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<Transaction> {
    private LayoutInflater inflater;
    private int layout;
    private FileHelper fileHelper;
    private ArrayList<Transaction> transactions;

    public TransactionAdapter(Context context, int resource, ArrayList<Transaction> transactions) {
        super(context, resource, transactions);
        this.transactions = transactions;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(this.layout, parent, false);
        final Transaction transaction = transactions.get(position);
        TextView date = (TextView) view.findViewById(R.id.date);
        fileHelper = new FileHelper(getContext());
//        TextView time = (TextView) view.findViewById(R.id.time);
        TextView value = (TextView) view.findViewById(R.id.value);
        TextView title = (TextView) view.findViewById(R.id.postTitle);
        TextView kind = (TextView) view.findViewById(R.id.postKind);
        ConstraintLayout select = (ConstraintLayout) view.findViewById(R.id.selected);
        String [] ss = transaction.getCreatedAt().split("uu");
//        time.setText(ss[1]);
        String dates [] = ss[0].split(",");
        if (Maltabu.lang.equals("ru"))
            date.setText(dates[0]+" "+dates[1]);
        else
            date.setText(dates[0]+" "+dates[2]);
        if(transaction.getSource().equals("cabinet")) {
            title.setText(transaction.getTitle());
            value.setText("-" + transaction.getValue());
            if(transaction.getValue().equals("250")){
                kind.setText("Добавление в горячие");
            } else if(transaction.getValue().equals("150")){
                kind.setText("Поднятие наверх");
            }
        }
        else {
            value.setText("+" + transaction.getValue());
            kind.setText("Пополнение через Касса 24");
        }

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!transaction.getKind().equals("refill")) {
                    SecondThread thread = new SecondThread(transaction.getPostNumber());
                    thread.start();
                }
            }
        });

        return view;
    }


    public void getPost(String numb){
        Intent details = new Intent(getContext(), ShowDetails.class);
        details.putExtra("postNumb", numb);
        getContext().startActivity(details);
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
        return ss2[2] +","+ss2[1];
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

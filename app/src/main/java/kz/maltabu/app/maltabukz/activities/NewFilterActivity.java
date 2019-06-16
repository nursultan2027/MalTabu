package kz.maltabu.app.maltabukz.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Region;

public class NewFilterActivity extends BaseActivity{
    private FileHelper fileHelper;
    private ArrayList regions = new ArrayList();
    private ArrayList<ArrayList<String>> citiesArr = new ArrayList<>();
    @BindView(R.id.titlet)TextView titleTextView;
    @BindView(R.id.textView7)TextView regionTextView;
    @BindView(R.id.textView12)TextView cityTextView;
    @BindView(R.id.textView13)TextView priceTextView;
    @BindView(R.id.textView8)TextView onlyPhotoTextView;
    @BindView(R.id.textView14)TextView onlyBarterTextView;
    @BindView(R.id.texxx)TextView emergencyTextView;
    @BindView(R.id.regSpin)Spinner regionSpin;
    @BindView(R.id.citySpin)Spinner citySpin;
    @BindView(R.id.arrr)ImageView backArrow;
    @BindView(R.id.editText7)EditText priceBeforeEditText;
    @BindView(R.id.editText8)EditText priceAfterEditText;
    @BindView(R.id.select21)Button showResultButton;
    @BindView(R.id.select22)Button clearFiltersButton;
    @BindView(R.id.checkBox2)CheckBox onlyPhotoCheckbox;
    @BindView(R.id.checkBox3)CheckBox barterCheckbox;
    @BindView(R.id.checkBox)CheckBox emergencyCheckbox;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fiter);
        fileHelper = new FileHelper(this);
        ButterKnife.bind(this);
        getCities();
    }

    public void getCities(){
        try {
            for (int i=0; i<fileHelper.getRegionsFromFile().size();i++) {
                Region region = fileHelper.getRegionsFromFile().get(i);
                regions.add(region);
                ArrayList<String> cityarr = new ArrayList<>();
                cityarr.add(LocaleHelper.setLocale(this, Maltabu.lang).getResources().getString(R.string.chooseCity));
                for (int j=0; j<region.cities.size(); j++){
                    cityarr.add(region.cities.get(j).getName());
                }
                citiesArr.add(cityarr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


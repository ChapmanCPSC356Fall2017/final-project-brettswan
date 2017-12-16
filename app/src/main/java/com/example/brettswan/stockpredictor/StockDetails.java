package com.example.brettswan.stockpredictor;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class StockDetails extends Fragment {

    private TextView stockName;
    private TextView stockSym;
    private TextView stockPrice;
    private TextView stockPercentChange;
    private TextView stockVolume;
    private TextView stockMarketCap;
    private String startDate;
    private EditText startDateET;
    private Button Analyze;
    private LinearLayout spread;
    private LinearLayout aveVolume;
    private TextView spreadText;
    private TextView volumeText;

    private String startMon;
    private String startDay;
    private String startYear;

    private Boolean gotResult;

    private JsonResult stockData = JsonResult.getData();
    private HomeResult homeData = HomeResult.getData();

    public StockDetails() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stock_details, container, false);
        this.homeData = HomeResult.getData();
        this.stockData = JsonResult.getData();

        this.stockName = v.findViewById(R.id.stockName_TV);
        this.stockSym = v.findViewById(R.id.stockSymbol_TV);
        this.stockPrice = v.findViewById(R.id.stockPrice_TV);
        this.stockPercentChange = v.findViewById(R.id.stockChangePercent_TV);
        this.stockVolume = v.findViewById(R.id.stockVolume_TV);
        this.stockMarketCap = v.findViewById(R.id.stockMarketCap_TV);
        this.startDateET = v.findViewById(R.id.RunAnalysis_BT);
        this.Analyze = v.findViewById(R.id.analyze);
        this.spread = v.findViewById(R.id.Spread);
        this.aveVolume = v.findViewById(R.id.volume);
        this.gotResult = false;
        this.spreadText = v.findViewById(R.id.spreadText);
        this.volumeText = v.findViewById(R.id.volumeText);

        Analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MAKING REQUEST","TO HOME");
                API.homeLookup(stockSym.getText().toString(),startDate,new API.Callback(){
                    @Override
                    public void onFailure(Exception e)
                    {
                        Log.e("FAILURE", "Something happened", e);

                    }

                    @Override
                    public void onResult(JsonResult results) {

                    }

                    @Override
                    public void onHomeResult(final HomeResult results)
                    {
                        Log.i("SUCCESS", "It works i promise");

                        homeData.price_spread = results.price_spread;
                        homeData.average_volume = results.average_volume;
                        homeData.symbol = results.symbol;

                        getActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                startDateET.setVisibility(View.GONE);
                                Analyze.setVisibility(View.GONE);

                                spreadText.setText(homeData.price_spread.toString());
                                volumeText.setText(homeData.average_volume.toString());

                                spread.setVisibility(View.VISIBLE);
                                aveVolume.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }
        });

        startDateET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                startDate = editable.toString();
                String[] dateSplit = startDate.split("/");

                if (dateSplit.length == 3) {
                    startDate = dateSplit[0] + "_" + dateSplit[1] + "_" + dateSplit[2];
                }
            }
        });


        if (stockData.percentChange > 0){
            stockPercentChange.setTextColor(Color.parseColor("#FF88E264"));
        }
        else{
            stockPercentChange.setTextColor(Color.parseColor("#FFE43D3D"));
        }

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        stockName.setText(stockData.name);
        stockSym.setText(stockData.symbol);
        stockPrice.setText("$"+numberFormat.format(stockData.currentPrice));
        stockPercentChange.setText(stockData.percentChange.toString().substring(0,4)+"%");
        stockVolume.setText(numberFormat.format(stockData.volume));
        stockMarketCap.setText("$"+numberFormat.format(stockData.marketCap));

        return v;
    }

}

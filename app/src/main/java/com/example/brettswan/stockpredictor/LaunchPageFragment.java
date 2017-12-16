package com.example.brettswan.stockpredictor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.util.Log;

public class LaunchPageFragment extends Fragment {


    private EditText stockSearch;
    private Button submitSearch;
    private String chosenStock;

    private JsonResult stockData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.launch_page_fragment, container, false);

        this.stockSearch = (EditText) v.findViewById(R.id.searchView);
        this.submitSearch = (Button) v.findViewById(R.id.submit);
        this.stockData = JsonResult.getData();

        stockSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                chosenStock = stockSearch.getText().toString();
                if (chosenStock != "") {
                    submitSearch.setVisibility(View.VISIBLE);
                } else {
                    submitSearch.setVisibility(View.INVISIBLE);
                }
            }
        });

        submitSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API.webLookup(chosenStock,new API.Callback(){
                    @Override
                    public void onFailure(Exception e)
                    {
                        Log.e("FAILURE", "Something happened", e);

                    }

                    @Override
                    public void onResult(final JsonResult results)
                    {
                        Log.i("SUCCESS", "It works i promise");

                        Log.i("name",results.name);
                        Log.i("price",results.currentPrice.toString());

                        stockData.currentPrice = results.currentPrice;
                        stockData.name = results.name;
                        stockData.symbol = results.symbol;
                        stockData.marketCap = results.marketCap;
                        stockData.percentChange = results.percentChange;
                        stockData.volume = results.volume;

                        showOtherFragment();


                    }

                    @Override
                    public void onHomeResult(HomeResult results) {

                    }

                });

            }
        });
        return v;

    }

    public void showOtherFragment()
    {
        Fragment fr=new StockDetails();
        FragmentChangeListener fc=(FragmentChangeListener)getActivity();
        fc.replaceFragment(fr);
    }






}

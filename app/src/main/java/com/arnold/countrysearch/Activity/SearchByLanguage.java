package com.arnold.countrysearch.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.arnold.countrysearch.Adapter.CountryAdapter;
import com.arnold.countrysearch.JavaClasses.MySingleton;
import com.arnold.countrysearch.R;
import com.arnold.countrysearch.database.CountryDatabase;
import com.arnold.countrysearch.entites.Country;
import com.arnold.countrysearch.listeners.CountryListeners;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchByLanguage extends AppCompatActivity implements CountryListeners {

    EditText search;
    String countryName, countryBorders, population, subregion, region, capital, flag, languages,topLevelDomain,alpha2Code,alpha3Code
            ,callingCodes,altSpellings,latlng,demonym,area,gini,timezones,nativeName,numericCode,currencies,cioc;
    private RecyclerView countryRecyclerView;
    private List<Country> countriesList;
    private CountryAdapter countryAdapter;
    JSONObject cityInfo;
    String  url;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.holo_red));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.background));
        setContentView(R.layout.searchbylanguage);

        search = findViewById(R.id.search);
        countryRecyclerView = findViewById(R.id.RecyclerView);
        loadingBar = new ProgressDialog(this);

        search.setSelection(search.getText().length());
        search.requestFocus();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchByLanguage.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        countryRecyclerView.setLayoutManager(linearLayoutManager);

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletingData();
            }
        });

        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletingData();
                Fetching();
            }
        });
        findViewById(R.id.info).setOnClickListener(v -> Info());

    }


    private void Fetching() {
        loadingBar.setTitle("Fetching Details");
        loadingBar.setMessage("Please Wait while we are receiving the details for you...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.getProgress();
        loadingBar.show();
        url = "https://restcountries.eu/rest/v2/lang/"+search.getText().toString();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        languages = null;
                        cityInfo = response.getJSONObject(i);
                        countryName = cityInfo.getString("name");
                        countryBorders = cityInfo.getString("borders");
                        population = cityInfo.getString("population");
                        subregion = cityInfo.getString("subregion");
                        region = cityInfo.getString("region");
                        capital = cityInfo.getString("capital");
                        topLevelDomain = cityInfo.getString("topLevelDomain");
                        alpha2Code = cityInfo.getString("alpha2Code");
                        alpha3Code = cityInfo.getString("alpha3Code");
                        callingCodes = cityInfo.getString("callingCodes");
                        altSpellings = cityInfo.getString("altSpellings");
                        latlng = cityInfo.getString("latlng");
                        demonym = cityInfo.getString("demonym");
                        area = cityInfo.getString("area");
                        gini = cityInfo.getString("gini");
                        timezones = cityInfo.getString("timezones");
                        nativeName = cityInfo.getString("nativeName");
                        numericCode = cityInfo.getString("numericCode");
                        currencies = cityInfo.getString("currencies");
                        cioc = cityInfo.getString("cioc");
                        JSONArray movies = cityInfo.getJSONArray("languages");
                        for (int j = 0; j < movies.length(); j++) {
                            JSONObject details = movies.getJSONObject(j);
                            String title = details.getString("name");
                            String title2 = details.getString("nativeName");
                            languages += title+" : "+title2 + " ,\n";
                        }
                        languages = languages.replaceAll("null","");
                        languages = languages.substring(0,languages.length() - 2);
                        flag = cityInfo.getString("flag");
                        Country country = new Country();
                        country.setName(countryName);
                        country.setBorders(countryBorders);
                        country.setRegion(region);
                        country.setSubRegion(subregion);
                        country.setLanguages(languages);
                        country.setPopulation(population);
                        country.setCapital(capital);
                        country.setFlagPath(flag);
                        country.setTopLevelDomain(topLevelDomain);
                        country.setAlpha2Code(alpha2Code);
                        country.setAlpha3Code(alpha3Code);
                        country.setCallingCodes(callingCodes);
                        country.setAltSpellings(altSpellings);
                        country.setLatlng(latlng);
                        country.setDemonym(demonym);
                        country.setArea(area);
                        country.setGini(gini);
                        country.setTimezones(timezones);
                        country.setNativeName(nativeName);
                        country.setNumericCode(numericCode);
                        country.setCurrencies(currencies);
                        country.setCioc(cioc);

                        //Async task used as the saving of the data to room database will be executed on the background thread
                        @SuppressLint("StaticFieldLeak")
                        class SaveNoteTask extends AsyncTask<Void, Void, Void> {

                            @Override
                            protected Void doInBackground(Void... voids) {
                                CountryDatabase.getCountryDatabase(getApplicationContext()).countryDao().insertCountry(country); //inserting data in room database
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);

                            }
                        }

                        new SaveNoteTask().execute();
                    }

                    Display();
                    loadingBar.dismiss();//loading bar dismissed as the data has been loaded and is saved in the database
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> showToast("Error, Please check the language code and try again"));loadingBar.dismiss(); //in case of any error this toast will be executed

        MySingleton.getInstance(SearchByLanguage.this).addToRequestQueue(request);

    }

    private void Display() {
        countriesList = new ArrayList<>();
        countryAdapter = new CountryAdapter(countriesList, SearchByLanguage.this);
        countryRecyclerView.setHasFixedSize(true);
        countryRecyclerView.setAdapter(countryAdapter);
        getCountries();
    }

    private void getCountries() {
        @SuppressLint("StaticFieldLeak")
        class GetCountriesTask extends AsyncTask<Void, Void, List<Country>> {

            @Override
            protected List<Country> doInBackground(Void... voids) {
                return CountryDatabase.getCountryDatabase(getApplicationContext())
                        .countryDao().getAllCountries();
            }

            @Override
            protected void onPostExecute(List<Country> countries) {
                super.onPostExecute(countries);
                countriesList.addAll(countries);
                countryAdapter.notifyDataSetChanged();

            }
        }

        new GetCountriesTask().execute();
    }

    public void DeletingData(){
        class Delete extends AsyncTask<Void, Void, List<Country>> {
            @Override
            protected List<Country> doInBackground(Void... voids) {
                CountryDatabase.getCountryDatabase(getApplicationContext()).countryDao().deleteAll();
                return null;
            }

            @Override
            protected void onPostExecute(List<Country> countries) {
                super.onPostExecute(countries);
            }
        }
        new Delete().execute();
        countriesList = new ArrayList<>();
        countryAdapter = new CountryAdapter(countriesList, SearchByLanguage.this);
        countryRecyclerView.setHasFixedSize(true);
        countryRecyclerView.setAdapter(countryAdapter);
    }

    void showToast(String message) {
        Toast toast = new Toast(SearchByLanguage.this);

        @SuppressLint("InflateParams") View view = LayoutInflater.from(SearchByLanguage.this)
                .inflate(R.layout.toast_layout, null);

        TextView tvMessage = view.findViewById(R.id.Message); //text view from the custom toast layout
        tvMessage.setText(message);

        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onItemClicked(Country country, int position) {

    }

    private void Info() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchByLanguage.this,R.style.AlertDialog);
        builder.setTitle("Important Note");
        builder.setCancelable(false);

        final TextView groupNameField = new TextView(SearchByLanguage.this);
        groupNameField.setText("1) Type the language code of the country. \n\n2) After typing the code in the box press the search image in front of the text, if the code put by you is correct the results will be shown else a red box will be pop up showing some error.");
        groupNameField.setPadding(20,30,20,20);
        groupNameField.setTextColor(Color.BLACK);

        groupNameField.setBackgroundColor(Color.WHITE);
        builder.setView(groupNameField);

        builder.setPositiveButton("Got it", (dialogInterface, i) -> dialogInterface.cancel());

        builder.show();
    }
}
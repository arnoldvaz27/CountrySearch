package com.arnold.countrysearch.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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

@SuppressWarnings({"deprecation"})
public class WorldWeb extends AppCompatActivity implements CountryListeners {
    ImageView imageView;
    String countryName, countryBorders, population, subregion, region, capital, flag, languages,topLevelDomain,area,latlng,numericCode,nativeName;
    private RecyclerView countryRecyclerView;
    private List<Country> countriesList;
    private CountryAdapter countryAdapter;
    JSONObject cityInfo;
    private ProgressDialog loadingBar;
    String url;
    TextView worldWeb;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.holo_red));
        setContentView(R.layout.worldweb);

         //initializing
        loadingBar = new ProgressDialog(this);
        imageView = findViewById(R.id.delete);
        countryRecyclerView = findViewById(R.id.RecyclerView);
        worldWeb = findViewById(R.id.worldWeb);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WorldWeb.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        countryRecyclerView.setLayoutManager(linearLayoutManager);

        //Checking whether the device android version is 9/P or above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            findViewById(R.id.card).setVisibility(View.VISIBLE);
            findViewById(R.id.Message).setSelected(true);
        }

        DataRefreshed();
        url = "https://restcountries.eu/rest/v2/all";
        worldWeb.setText("World Web");
        Fetching();

        //imageview with click listener, which will delete the data after the user clicks on this view
        imageView.setOnClickListener(v -> DeletingData());

        //imageview with click listener, which will reload the whole activity without any animation.
        findViewById(R.id.refresh).setOnClickListener(v -> {
            Intent i = new Intent(WorldWeb.this, WorldWeb.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(i);
            overridePendingTransition(0, 0);
        });

        findViewById(R.id.info).setOnClickListener(v -> Info());


    }
    private void Fetching() {
        //loading bar to show the user that the data is retrieving
        loadingBar.setTitle("Fetching Details");
        loadingBar.setMessage("Please Wait while we are receiving the details for you...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.getProgress();
        loadingBar.show();

        //url of the api and the region = asia
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int b = response.length();
                worldWeb.append(" (Total : "+b+" Countries)" );
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
                        area = cityInfo.getString("area");
                        latlng = cityInfo.getString("latlng");
                        numericCode = cityInfo.getString("numericCode");
                        nativeName = cityInfo.getString("nativeName");
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
                        country.setArea(area);
                        country.setNativeName(nativeName);
                        country.setLatlng(latlng);
                        country.setNumericCode(numericCode);

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
                    loadingBar.dismiss(); //loading bar dismissed as the data has been loaded and is saved in the database
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error ->  Toast.makeText(WorldWeb.this, "Error, Please make sure the internet is on", Toast.LENGTH_SHORT).show());loadingBar.dismiss(); //loading bar dismissed as the data has been loaded and is saved in the database); //in case of any error this toast will be executed

        MySingleton.getInstance(WorldWeb.this).addToRequestQueue(request);

    }

    //method to display all the data that has been saved in the room database
    private void Display() {
        countriesList = new ArrayList<>();
        countryAdapter = new CountryAdapter(countriesList, WorldWeb.this);
        countryRecyclerView.setHasFixedSize(true);
        countryRecyclerView.setAdapter(countryAdapter);
        getCountries();
    }

    //method to get all the countries from the database with all the data so that the display method can display it
    private void getCountries() {
        @SuppressLint("StaticFieldLeak")
        class GetCountriesTask extends AsyncTask<Void, Void, List<Country>> {

            @Override
            protected List<Country> doInBackground(Void... voids) {
                return CountryDatabase.getCountryDatabase(getApplicationContext())
                        .countryDao().getAllCountries();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void onPostExecute(List<Country> countries) {
                super.onPostExecute(countries);
                countriesList.addAll(countries);
                countryAdapter.notifyDataSetChanged();

            }
        }

        new GetCountriesTask().execute();
    }

    //item clicked method from the listener interface
    @Override
    public void onItemClicked(Country country, int position) {
        //nothing to do here as there is no onclick happening till now on the view, it can be used for further upgrades in the app
    }

    //method for deleting all the data from the room database
    public void DeletingData(){
        @SuppressLint("StaticFieldLeak")
        class Delete extends AsyncTask<Void, Void, List<Country>> {
            @Override
            protected List<Country> doInBackground(Void... voids) {
                CountryDatabase.getCountryDatabase(getApplicationContext()).countryDao().deleteAll();
                return null;
            }

            @Override
            protected void onPostExecute(List<Country> countries) {
                super.onPostExecute(countries);
                showToast("Data Deleted");
            }
        }
        new Delete().execute();
        countriesList = new ArrayList<>();
        countryAdapter = new CountryAdapter(countriesList, WorldWeb.this);
        countryRecyclerView.setHasFixedSize(true);
        countryRecyclerView.setAdapter(countryAdapter);
    }

    public void DataRefreshed(){
        @SuppressLint("StaticFieldLeak")
        class Delete extends AsyncTask<Void, Void, List<Country>> {
            @Override
            protected List<Country> doInBackground(Void... voids) {
                CountryDatabase.getCountryDatabase(getApplicationContext()).countryDao().deleteAll();
                return null;
            }

            @Override
            protected void onPostExecute(List<Country> countries) {
                super.onPostExecute(countries);
                showToast("Data Refreshed");
            }
        }
        new Delete().execute();
        countriesList = new ArrayList<>();
        countryAdapter = new CountryAdapter(countriesList, WorldWeb.this);
        countryRecyclerView.setHasFixedSize(true);
        countryRecyclerView.setAdapter(countryAdapter);
    }

    //method to display custom toast to the user
    void showToast(String message) {
        Toast toast = new Toast(WorldWeb.this);

        @SuppressLint("InflateParams") View view = LayoutInflater.from(WorldWeb.this)
                .inflate(R.layout.toast_layout, null);

        TextView tvMessage = view.findViewById(R.id.Message); //text view from the custom toast layout
        tvMessage.setText(message);

        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }


    @SuppressLint("SetTextI18n")
    private void Info() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WorldWeb.this,R.style.AlertDialog);
        builder.setTitle("Important Note");
        builder.setCancelable(false);

        final TextView groupNameField = new TextView(WorldWeb.this);
        groupNameField.setText("Fields that have null means there is no data related to that.");
        groupNameField.setPadding(20,30,20,20);
        groupNameField.setTextColor(Color.BLACK);

        groupNameField.setBackgroundColor(Color.WHITE);
        builder.setView(groupNameField);

        builder.setPositiveButton("Got it", (dialogInterface, i) -> dialogInterface.cancel());

        builder.show();
    }
}
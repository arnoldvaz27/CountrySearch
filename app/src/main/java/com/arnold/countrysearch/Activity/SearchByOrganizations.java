package com.arnold.countrysearch.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

public class SearchByOrganizations extends AppCompatActivity implements CountryListeners {

    ImageView imageView;
    String countryName, countryBorders, population, subregion, region, capital, flag, languages,topLevelDomain,area,latlng,numericCode,nativeName;
    private RecyclerView countryRecyclerView;
    private List<Country> countriesList;
    private CountryAdapter countryAdapter;
    JSONObject cityInfo;
    private ProgressDialog loadingBar;
    String url;
    TextView associationName;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.holo_red));
        setContentView(R.layout.searchbyorganizations);


        //initializing
        loadingBar = new ProgressDialog(this);
        imageView = findViewById(R.id.delete);
        countryRecyclerView = findViewById(R.id.RecyclerView);
        associationName = findViewById(R.id.associationName);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchByOrganizations.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        countryRecyclerView.setLayoutManager(linearLayoutManager);

        //Checking whether the device android version is 9/P or above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            findViewById(R.id.card).setVisibility(View.VISIBLE);
            findViewById(R.id.Message).setSelected(true);
        }

        DataRefreshed();
        url = "https://restcountries.eu/rest/v2/regionalbloc/saarc";
        associationName.setText("South Asian Association for Regional Cooperation");
        Fetching();

        //imageview with click listener, which will delete the data after the user clicks on this view
        imageView.setOnClickListener(v -> DeletingData());associationName.setText("World web");


        //imageview with click listener, which will reload the whole activity without any animation.
        findViewById(R.id.refresh).setOnClickListener(v -> {
            Intent i = new Intent(SearchByOrganizations.this, SearchByOrganizations.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(i);
            overridePendingTransition(0, 0);
        });

        findViewById(R.id.menu).setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(SearchByOrganizations.this, findViewById(R.id.menu));
            popupMenu.getMenuInflater().inflate(R.menu.filter_organization, popupMenu.getMenu());
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.EU) {
                    associationName.setText("");
                    DataRefreshed();
                    url = "https://restcountries.eu/rest/v2/regionalbloc/eu";
                    associationName.setText("European Union");
                    Fetching();
                }
                if (item.getItemId() == R.id.EFTA) {
                    associationName.setText("");
                    DataRefreshed();
                    url = "https://restcountries.eu/rest/v2/regionalbloc/efta";
                    associationName.setText("European Free Trade Association");
                    Fetching();
                }
                if (item.getItemId() == R.id.CARICOM) {
                    associationName.setText("");
                    DataRefreshed();
                    url = "https://restcountries.eu/rest/v2/regionalbloc/caricom";
                    associationName.setText("Caribbean Community");
                    Fetching();
                }
                if (item.getItemId() == R.id.PA) {
                    associationName.setText("");
                    DataRefreshed();
                    url = "https://restcountries.eu/rest/v2/regionalbloc/pa";
                    associationName.setText("Pacific Alliance");
                    Fetching();
                }
                if (item.getItemId() == R.id.AU) {
                    associationName.setText("");
                    DataRefreshed();
                    url = "https://restcountries.eu/rest/v2/regionalbloc/au";
                    associationName.setText("African Union");
                    Fetching();
                }
                if (item.getItemId() == R.id.USAN) {
                    associationName.setText("");
                    DataRefreshed();
                    url = "https://restcountries.eu/rest/v2/regionalbloc/usan";
                    associationName.setText("Union of South American Nations");
                    Fetching();
                }
                if (item.getItemId() == R.id.EEU) {
                    associationName.setText("");
                    DataRefreshed();
                    url = "https://restcountries.eu/rest/v2/regionalbloc/eeu";
                    associationName.setText("Eurasian Economic Union");
                    Fetching();
                }
                if (item.getItemId() == R.id.AL) {
                    associationName.setText("");
                    DataRefreshed();
                    url = "https://restcountries.eu/rest/v2/regionalbloc/al";
                    associationName.setText("Arab League");
                    Fetching();
                }
                if (item.getItemId() == R.id.ASEAN) {
                    associationName.setText("");
                    DataRefreshed();
                    url = "https://restcountries.eu/rest/v2/regionalbloc/asean";
                    associationName.setText("Association of Southeast Asian Nations");
                    Fetching();
                }
                if (item.getItemId() == R.id.CAIS) {
                    associationName.setText("");
                    DataRefreshed();
                    url = "https://restcountries.eu/rest/v2/regionalbloc/cais";
                    associationName.setText("Central American Integration System");
                    Fetching();
                }
                if (item.getItemId() == R.id.CEFTA) {
                    associationName.setText("");
                    DataRefreshed();
                    url = "https://restcountries.eu/rest/v2/regionalbloc/cefta";
                    associationName.setText("Central European Free Trade Agreement");
                    Fetching();
                }
                if (item.getItemId() == R.id.NAFTA) {
                    associationName.setText("");
                    DataRefreshed();
                    url = "https://restcountries.eu/rest/v2/regionalbloc/nafta";
                    associationName.setText("North American Free Trade Agreement");
                    Fetching();
                }
                if (item.getItemId() == R.id.SAARC) {
                    associationName.setText("");
                    DataRefreshed();
                    url = "https://restcountries.eu/rest/v2/regionalbloc/saarc";
                    associationName.setText("South Asian Association for Regional Cooperation");
                    Fetching();
                }
                return true;
            });
        });
    }

    //method for fetching the data from the specified url (api) and saving it in the room database locally
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
                associationName.append(" (Total : "+b+" Countries)" );
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
        }, error ->  Toast.makeText(SearchByOrganizations.this, "Error, Please make sure the internet is on", Toast.LENGTH_SHORT).show());loadingBar.dismiss(); //loading bar dismissed as the data has been loaded and is saved in the database); //in case of any error this toast will be executed

        MySingleton.getInstance(SearchByOrganizations.this).addToRequestQueue(request);

    }

    //method to display all the data that has been saved in the room database
    private void Display() {
        countriesList = new ArrayList<>();
        countryAdapter = new CountryAdapter(countriesList, SearchByOrganizations.this);
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
        countryAdapter = new CountryAdapter(countriesList, SearchByOrganizations.this);
        countryRecyclerView.setHasFixedSize(true);
        countryRecyclerView.setAdapter(countryAdapter);
    }

    public void DataRefreshed(){
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
        countryAdapter = new CountryAdapter(countriesList, SearchByOrganizations.this);
        countryRecyclerView.setHasFixedSize(true);
        countryRecyclerView.setAdapter(countryAdapter);
    }

    //method to display custom toast to the user
    void showToast(String message) {
        Toast toast = new Toast(SearchByOrganizations.this);

        @SuppressLint("InflateParams") View view = LayoutInflater.from(SearchByOrganizations.this)
                .inflate(R.layout.toast_layout, null);

        TextView tvMessage = view.findViewById(R.id.Message); //text view from the custom toast layout
        tvMessage.setText(message);

        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
package com.arnold.countrysearch.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.holo_red));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.background));
        setContentView(R.layout.activity_main);

        findViewById(R.id.SearchByCountry).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SearchByCountry.class)));
        findViewById(R.id.SearchByCurrency).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SearchByCurrency.class)));
        findViewById(R.id.SearchByLanguage).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SearchByLanguage.class)));
        findViewById(R.id.SearchByCapital).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SearchByCapital.class)));
        findViewById(R.id.SearchByCallingCode).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SearchByCallingCode.class)));
        findViewById(R.id.SearchByInitialLetter).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SearchByInitialLetter.class)));
    }


}
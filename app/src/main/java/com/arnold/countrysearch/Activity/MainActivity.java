package com.arnold.countrysearch.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.arnold.countrysearch.R;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.holo_red));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.background));
        setContentView(R.layout.activity_main);

        findViewById(R.id.more).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,MoreMenu.class)));
        findViewById(R.id.SearchByCountry).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SearchByCountry.class)));
        findViewById(R.id.SearchByCurrency).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SearchByCurrency.class)));
        findViewById(R.id.SearchByLanguage).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SearchByLanguage.class)));
        findViewById(R.id.SearchByCapital).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SearchByCapital.class)));
        findViewById(R.id.SearchByCallingCode).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SearchByCallingCode.class)));
        findViewById(R.id.SearchByInitialLetter).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SearchByInitialLetter.class)));
        findViewById(R.id.SearchByOrganizations).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SearchByOrganizations.class)));
        findViewById(R.id.SearchByRegion).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SearchByRegion.class)));
        findViewById(R.id.WorldWeb).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,WorldWeb.class)));
    }
}
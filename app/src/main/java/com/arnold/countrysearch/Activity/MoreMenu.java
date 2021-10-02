package com.arnold.countrysearch.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.arnold.countrysearch.R;

public class MoreMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.holo_red));
        setContentView(R.layout.more_menu);

        findViewById(R.id.back).setOnClickListener(v -> finish());

        findViewById(R.id.privacy).setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://programmingdrive.blogspot.com/2021/08/Privacy%20Policy%20For%20Country%20Search.html"))));
        findViewById(R.id.terms).setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://programmingdrive.blogspot.com/2021/08/Terms%20and%20Condition%20For%20Country%20Search.html"))));
        findViewById(R.id.developer).setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/arnoldvaz27/CountrySearch"))));
        findViewById(R.id.website).setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://linktr.ee/arnoldvaz"))));
        findViewById(R.id.youtube).setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/playlist?list=PLaas0iwT7cvsMKc2ZcjqvJygDuoDN7H2k"))));
    }
}
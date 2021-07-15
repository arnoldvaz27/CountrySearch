package com.arnold.countrysearch.JavaClasses;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.arnold.countrysearch.R;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class ImageFetch {

    private static OkHttpClient httpClient;

    // this method is used to fetch svg and load it into target imageview.

    public static void fetchSvg(Context context, String url, final ImageView target) {
        if (httpClient == null) {
            //used for caching the image and storing it locally so that the user can view the images in offline mode as well
            httpClient = new OkHttpClient.Builder()
                    .cache(new Cache(context.getCacheDir(), 5 * 1024 * 1024))
                    .build();
        }

        //used to display all the images that are the retrieved from the url into the imageview
        GlideToVectorYou
                .init()
                .with(context.getApplicationContext())
                .withListener(new GlideToVectorYouListener() {
                    @Override
                    public void onLoadFailed() {
                        Toast.makeText(context.getApplicationContext(), "Load failed, please connect to internet to fetch the flag", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResourceReady() {
                    }
                })
                .setPlaceHolder(R.drawable.android, R.drawable.android)
                .load(Uri.parse(url), target);
    }

}

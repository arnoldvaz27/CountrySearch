package com.arnold.countrysearch.JavaClasses;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//to handle the request made from the application and also reduce the work load on the application while retrieving the data
public class MySingleton {
    @SuppressLint("StaticFieldLeak")
    private static MySingleton instance;
    private RequestQueue requestQueue;
    @SuppressLint("StaticFieldLeak")
    private static Context ctx;

    private MySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();

    }

    public static synchronized MySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext()); //volley library to handle the request
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}

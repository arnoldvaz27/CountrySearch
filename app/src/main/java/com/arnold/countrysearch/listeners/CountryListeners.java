package com.arnold.countrysearch.listeners;

import com.arnold.countrysearch.entites.Country;

public interface CountryListeners {

    void onItemClicked(Country country, int position); //event listener after clicking on the view

}

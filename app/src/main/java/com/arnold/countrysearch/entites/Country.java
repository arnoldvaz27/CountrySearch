package com.arnold.countrysearch.entites;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//this class consists of all the columns that will be available in the room database
@Entity(tableName = "countries") //table name
public class Country implements Serializable {

    //declaration of the fields (columns in the table)
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String Name;

    @ColumnInfo(name = "capital")
    private String Capital;

    @ColumnInfo(name = "flag_path")
    private String FlagPath;

    @ColumnInfo(name = "region")
    private String Region;

    @ColumnInfo(name = "subregion")
    private String SubRegion;

    @ColumnInfo(name = "population")
    private String Population;

    @ColumnInfo(name = "borders")
    private String Borders;

    @ColumnInfo(name = "languages")
    private String Languages;

    @ColumnInfo(name = "topLevelDomain")
    private String topLevelDomain;

    @ColumnInfo(name = "alpha2Code")
    private String alpha2Code;

    @ColumnInfo(name = "alpha3Code")
    private String alpha3Code;

    @ColumnInfo(name = "callingCodes")
    private String callingCodes;

    @ColumnInfo(name = "altSpellings")
    private String altSpellings;

    @ColumnInfo(name = "latlng")
    private String latlng;

    @ColumnInfo(name = "demonym")
    private String demonym;

    @ColumnInfo(name = "area")
    private String area;

    @ColumnInfo(name = "gini")
    private String gini;

    @ColumnInfo(name = "timezones")
    private String timezones;

    @ColumnInfo(name = "nativeName")
    private String nativeName;

    @ColumnInfo(name = "numericCode")
    private String numericCode;

    @ColumnInfo(name = "currencies")
    private String currencies;

    @ColumnInfo(name = "cioc")
    private String cioc;


    //getters and setters of all the declared columns
    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getSubRegion() {
        return SubRegion;
    }

    public void setSubRegion(String subRegion) {
        SubRegion = subRegion;
    }

    public String getPopulation() {
        return Population;
    }

    public void setPopulation(String population) {
        Population = population;
    }

    public String getBorders() {
        return Borders;
    }

    public void setBorders(String borders) {
        Borders = borders;
    }

    public String getLanguages() {
        return Languages;
    }

    public void setLanguages(String languages) {
        Languages = languages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCapital() {
        return Capital;
    }

    public void setCapital(String capital) {
        Capital = capital;
    }

    public String getFlagPath() {
        return FlagPath;
    }

    public void setFlagPath(String flagPath) {
        this.FlagPath = flagPath;
    }

    public String getTopLevelDomain() {
        return topLevelDomain;
    }

    public void setTopLevelDomain(String topLevelDomain) {
        this.topLevelDomain = topLevelDomain;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public String getCallingCodes() {
        return callingCodes;
    }

    public void setCallingCodes(String callingCodes) {
        this.callingCodes = callingCodes;
    }

    public String getAltSpellings() {
        return altSpellings;
    }

    public void setAltSpellings(String altSpellings) {
        this.altSpellings = altSpellings;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getDemonym() {
        return demonym;
    }

    public void setDemonym(String demonym) {
        this.demonym = demonym;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getGini() {
        return gini;
    }

    public void setGini(String gini) {
        this.gini = gini;
    }

    public String getTimezones() {
        return timezones;
    }

    public void setTimezones(String timezones) {
        this.timezones = timezones;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public String getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String currencies) {
        this.currencies = currencies;
    }

    public String getCioc() {
        return cioc;
    }

    public void setCioc(String cioc) {
        this.cioc = cioc;
    }




}

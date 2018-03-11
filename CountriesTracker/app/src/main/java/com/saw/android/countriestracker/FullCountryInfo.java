package com.saw.android.countriestracker;

/**
 * Created by Android on 3/11/2018.
 */

public class FullCountryInfo extends BasicCountryInfo {
    private String region;
    private String subregion;
    private String capital;
    private int population;

    public FullCountryInfo(String name, String flag, String region, String subregion, String capital, int population) {
        super(name, flag);
        this.region = region;
        this.subregion = subregion;
        this.capital = capital;
        this.population = population;
    }
}

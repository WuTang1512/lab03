package com.example.listycitylab3;

import java.io.Serializable;

public class City implements Serializable {
    private String cityName;
    private String provinceName;

    public City(String cityName, String provinceName) {
        this.cityName = cityName; // constructor names updated for readability
        this.provinceName = provinceName;
    }

    // getter methods for City name and Province name
    public String getCityName() {
        return cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

//    // setter methods for City name and Province name did not need to be used
//    public void setCityName(String name) { // setter for City name
//        this.cityName = cityName;
//    }
//
//    public void setProvince(String province) { // setter for Province name
//        this.provinceName = provinceName;
//    }
}

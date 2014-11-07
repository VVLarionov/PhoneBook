package com.getjavajobs.larionov.phonebook.model;

/**
 * Created by Vlad on 29.09.2014.
 */
public class Phone {
    private int id;
    private String countryCode;
    private String cityCode;
    private String phoneNumber;
    private boolean isMobile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryCode(){
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean getMobile() {
        return isMobile;
    }

    public void setMobile(boolean isMobile) {
        this.isMobile = isMobile;
    }
}

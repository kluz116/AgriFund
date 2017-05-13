package com.agrifund.kluz.agrifund;

/**
 * Created by kluz on 2/13/17.
 */

public class District {

    private int id;
    private String district_name;

    public District(){

    }
    public District ( String district_name){
        //this.id = id;
        this.district_name = district_name;
    }


    public String getDistrictName() {
        return district_name;
    }

    public void setDistrictName(String district_name) {
        this.district_name = district_name;
    }

}

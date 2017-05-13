package com.agrifund.kluz.agrifund;

import java.io.Serializable;

/**
 * Created by kluz on 11/27/16.
 */
public class Fixture implements Serializable {
    private int id;
    private String league;
    private String date_created;
    private String hometeam;
    private String awayteam;
    private String time;


    public Fixture() {
    }

    public Fixture(int id, String league, String date_created, String hometeam, String awayteam, String time) {
        this.id = id;
        this.league = league;
        this.date_created = date_created;
        this.hometeam = hometeam;
        this.awayteam = awayteam;
        this.time = time;

    }
    public int getId(){
        return  id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getDateCreated() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public void setHometeam(String hometeam){this.hometeam = hometeam;}
    public String getHometeam(){return  hometeam;}

    public void setAwayteam(String awayteam){this.awayteam = awayteam;}
    public String getAwayteam(){return  awayteam;}

    public void setTime(String time){this.time = time;}
    public String getTime(){return  time;}
}


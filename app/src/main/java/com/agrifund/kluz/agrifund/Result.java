package com.agrifund.kluz.agrifund;

import java.io.Serializable;

/**
 * Created by kluz on 11/28/16.
 */
public class Result implements Serializable {
    private int id;
    private String league;
    private String status;
    private String hometeam;
    private String awayteam;
    private String homegoal;
    private String awaygoal;
    private String date;


    public Result() {
    }

    public Result(int id, String date, String league, String hometeam, String homegoal, String awayteam, String awaygoal,String status) {
        this.id = id;
        this.league = league;
        this.status = status;
        this.hometeam = hometeam;
        this.awayteam = awayteam;
        this.homegoal = homegoal;
        this.awaygoal = awaygoal;
        this.date = date;

    }
    public int getId(){
        return  id;
    }
    public void setId(int id){
        this.id = id;
    }
    public void SetDate(String date){
        this.date = date;
    }
    public  String GetDAte(){
        return  date;
    }
    public void setLeague(String league) {
        this.league = league;
    }
    public String getLeague() {
        return league;
    }

    public void setHometeam(String hometeam){this.hometeam = hometeam;}
    public String getHometeam(){return  hometeam;}

    public void setAwayteam(String awayteam){this.awayteam = awayteam;}
    public String getAwayteam(){return  awayteam;}

    public void setHomegoal(String homegoal){this.homegoal = homegoal;}
    public String getHomegoal(){return  homegoal;}

    public void setAwaygoal(String awaygoal){this.awaygoal = awaygoal;}
    public String getAwaygoal(){return  awaygoal;}

    public void setStatus() {
        this.status = status;
    }

    public String getStatus() {
        return  status;
    }
}



package com.ozeryavuzaslan;

import java.util.ArrayList;
import java.util.Collections;

public class League <T extends Team>{
    private String leagueName;
    private ArrayList<T> leagueArrayList;
    public static int objectCount = 0;

    public League(String leagueName){
        setLeagueName(leagueName);
        setLeagueArrayList(new ArrayList<>());
        objectCount++;
    }

    public void addLeague(T team){
        if (getLeagueArrayList().contains(team))
            System.out.println(team.getTeamName() + " is already in the " + getLeagueName() + " league.");
        else{
            System.out.println(team.getTeamName() + " has been added to the " + getLeagueName());
            getLeagueArrayList().add(team);
        }
    }

    public void showLeagueTable(){
        Collections.sort(getLeagueArrayList());

        for (T team: getLeagueArrayList())
            System.out.println(team.getTeamName() + ": " + team.getScore() + " Total won: " + team.getWon() + " Total lost: " + team.getLost() + " Total tied: " + team.getTied());
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public ArrayList<T> getLeagueArrayList() {
        return leagueArrayList;
    }

    public void setLeagueArrayList(ArrayList<T> leagueArrayList) {
        this.leagueArrayList = leagueArrayList;
    }
}

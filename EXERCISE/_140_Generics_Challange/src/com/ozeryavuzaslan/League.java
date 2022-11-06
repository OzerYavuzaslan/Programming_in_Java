package com.ozeryavuzaslan;

import java.util.ArrayList;
import java.util.Collections;

public class League <T extends Team>{
    private String leagueName;
    private ArrayList<T> leagueArrayList;

    public League(String leagueName) {
        setLeagueName(leagueName);
        setLeagueArrayList(new ArrayList<>());
    }

    public boolean addLeague(T team){
        if (getLeagueArrayList().contains(team))
            return false;

        getLeagueArrayList().add(team);
        return true;
    }

    public void showLeagueTable(){
        Collections.sort(getLeagueArrayList());

        for (T team: getLeagueArrayList())
            System.out.println(team.getTeamName() + ": " + team.ranking());
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

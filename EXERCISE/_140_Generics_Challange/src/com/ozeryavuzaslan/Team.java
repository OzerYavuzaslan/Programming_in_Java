package com.ozeryavuzaslan;

import java.util.ArrayList;

public class Team <T extends Player> implements Comparable<Team<T>>{
    private String teamName;
    private int won = 0;
    private int lost = 0;
    private int played = 0;
    private int tied = 0;
    private int score = 0;
    public static int objectCount = 0;
    private ArrayList<T> teamArrayList;

    public Team(String teamName) {
       setTeamName(teamName);
       setTeamArrayList(new ArrayList<>());
       objectCount++;
    }

    public void addPlayer(T player){
        if (getTeamArrayList().contains(player))
            System.out.println(player.getName() + " is already in the " + getTeamName() + " team.");
        else{
            System.out.println(player.getName() + " has been added to the " + getTeamName());
            getTeamArrayList().add(player);
        }
    }

    public void matchResult(Team<T> opponentTeam, int firstTeamStatus, int secondTeamStatus){
        String message;

        if (firstTeamStatus > secondTeamStatus){
            setWon(getWon() + 1);
            message = " beat ";
        }
        else if (firstTeamStatus < secondTeamStatus){
            setLost(getLost() + 1);
            message = " lost to ";
        }
        else {
            setTied(getTied() + 1);
            message = " draw with ";
        }

        setPlayed(getPlayed() + 1);

        if (opponentTeam != null){
            System.out.println(getTeamName() + message + opponentTeam.getTeamName());
            opponentTeam.matchResult(null, secondTeamStatus, firstTeamStatus); //store opponent team's results as well.
        }
    }

    private int ranking(){
        setScore((getWon() * 2) + 1);
        return getScore();
    }

    @Override
    public int compareTo(Team<T> opponentTeam) {
        if (opponentTeam.ranking() > this.ranking())
            return 1;
        else if (opponentTeam.ranking() < this.ranking())
            return -1;

        return 0;
    }

    public int numberOfPlayers(){
        return getTeamArrayList().size();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getTied() {
        return tied;
    }

    public void setTied(int tied) {
        this.tied = tied;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<T> getTeamArrayList() {
        return teamArrayList;
    }

    public void setTeamArrayList(ArrayList<T> teamArrayList) {
        this.teamArrayList = teamArrayList;
    }
}

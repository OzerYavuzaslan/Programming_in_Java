package com.ozeryavuzaslan;

import java.util.ArrayList;

public class Team <T extends Player> implements Comparable<Team<T>>{ //allowing only Player type
    private String teamName;
    private int played = 0;
    private int won = 0;
    private int lost = 0;
    private int tied = 0;
    private int teamPoint = 0;
    private ArrayList<T> teamMembersArrayList;

    public Team(String teamName) {
        setTeamName(teamName);
        setTeamMembersArrayList(new ArrayList<>());
    }

    public void addPlayer(T player){
        if (getTeamMembersArrayList().contains(player))
            System.out.println(player.getName() + " is already in the " + getTeamName() + " team.");
        else {
            getTeamMembersArrayList().add(player);
            System.out.println(player.getName() + " has been added to " + getTeamName() + " team.");
        }
    }

    public void matchResults(Team<T> opponentTeam, int firstTeamScore, int secondTeamScore){
        String message;

        if (firstTeamScore > secondTeamScore) {
            setWon(getWon() + 1);
            message = " beat ";
        }
        else if (firstTeamScore < secondTeamScore) {
            setLost(getLost() + 1);
            message = " lost to ";
        }
        else {
            setTied(getTied() + 1);
            message = " draw with ";
        }

        setPlayed(getPlayed() + 1);
        this.ranking();

        if (opponentTeam != null){
            System.out.println(getTeamName() + message + opponentTeam.getTeamName());
            matchResults(null, secondTeamScore, firstTeamScore);
        }
    }

    public int ranking(){
        setTeamPoint((getWon() * 2) + getTied());
        return getTeamPoint();
    }

    public int numberOfPlayers(){
        return getTeamMembersArrayList().size();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
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

    public int getTied() {
        return tied;
    }

    public void setTied(int tied) {
        this.tied = tied;
    }

    public int getTeamPoint() {
        return teamPoint;
    }

    public void setTeamPoint(int teamPoint) {
        this.teamPoint = teamPoint;
    }

    public ArrayList<T> getTeamMembersArrayList() {
        return teamMembersArrayList;
    }

    private void setTeamMembersArrayList(ArrayList<T> teamMembersArrayList) {
        this.teamMembersArrayList = teamMembersArrayList;
    }

    @Override
    public int compareTo(Team<T> team) {
        if (this.ranking() > team.ranking())
            return -1;
        else if (this.ranking() < team.ranking())
            return 1;

        return 0;
    }
}

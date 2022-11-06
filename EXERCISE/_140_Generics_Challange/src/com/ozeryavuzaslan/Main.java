package com.ozeryavuzaslan;

// ArrayList<Team> teams;
// Collections.sort(teams);
// Create a generic class to implement a league table for a sport.
// The class should allow teams to be added to the list, and store
// a list of teams that belong to the league.
//
// Your class should have a method to print out the teams in order,
// with the team at the top of the league printed first.
//
// Only teams of the same type should be added to any particular
// instance of the league class - the program should fail to compile
// if an attempt is made to add an incompatible team.

public class Main {
    public static void main(String[] args) {
        League<Team<FootballPlayer>> footballLeague = new League<>("AFL");
        Team<FootballPlayer> adelaideCrows = new Team<>("Adelaide Crows");
        Team<FootballPlayer> melbourne = new Team<>("Melbourne");
        Team<FootballPlayer> hawthorn= new Team<>("Hawthorn");
        Team<FootballPlayer> fremantle= new Team<>("Fremantle");
        Team<BaseballPlayer> baseballTeam = new Team<>("Chicago Cubs");

        hawthorn.matchResults(fremantle, 1, 0);
        hawthorn.matchResults(adelaideCrows, 3, 8);

        adelaideCrows.matchResults(fremantle, 2, 1);

        footballLeague.addLeague(adelaideCrows);
        footballLeague.addLeague(melbourne);
        footballLeague.addLeague(hawthorn);
        footballLeague.addLeague(fremantle);

//        footballLeague.add(baseballTeam);
        footballLeague.showLeagueTable();

        BaseballPlayer pat = new BaseballPlayer("Pat", 28, 62.64D);
        BasketballPlayer jordan = new BasketballPlayer("M. Jordan", 50, 80.25D);
        Team rawTeam = new Team("Raw Team");
        rawTeam.addPlayer(jordan); // unchecked warning
        rawTeam.addPlayer(pat);     // unchecked warning

        footballLeague.addLeague(rawTeam);     // unchecked warning

        League<Team> rawLeague = new League<>("Raw");
        rawLeague.addLeague(adelaideCrows);     // no warning
        rawLeague.addLeague(baseballTeam);    // no warning
        rawLeague.addLeague(rawTeam);         // no warning

        League reallyRaw = new League("Really raw");
        reallyRaw.addLeague(adelaideCrows);     // unchecked warning
        reallyRaw.addLeague(baseballTeam);    // unchecked warning
        reallyRaw.addLeague(rawTeam);
    }
}
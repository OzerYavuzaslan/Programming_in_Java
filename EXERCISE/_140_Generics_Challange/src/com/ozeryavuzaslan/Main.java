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
        League<Team<SoccerPlayer>> soccerLeague = new League<>("Süper Lig");
        BaseballPlayer ozer = new BaseballPlayer("Özer", 34);
        BaseballPlayer bayram = new BaseballPlayer("Bayram", 27);
        BaseballPlayer elif = new BaseballPlayer("Elif", 23);

        BasketballPlayer mJordan = new BasketballPlayer("M. Jordan", 55);
        BasketballPlayer lJames = new BasketballPlayer("L. James", 40);
        BasketballPlayer aDavis = new BasketballPlayer("A. Davis", 33);

        SoccerPlayer banu = new SoccerPlayer("Banu", 32);
        SoccerPlayer hagi = new SoccerPlayer("Hagi", 55);
        SoccerPlayer sergen = new SoccerPlayer("Sergen", 44);

        Team<BaseballPlayer> bostonRedSox = new Team<>("Boston Red Sox");
        Team<BaseballPlayer> chicagoCubs = new Team<>("Chicago Cubs");
        Team<BaseballPlayer> chicagoWhiteSox = new Team<>("Chicago White Sox");
        bostonRedSox.addPlayer(ozer);
        chicagoCubs.addPlayer(bayram);
        chicagoWhiteSox.addPlayer(elif);

        Team<BasketballPlayer> chicagoBulls = new Team<>("Chicago Bulls");
        Team<BasketballPlayer> bostonCeltics = new Team<>("Boston Celtics");
        Team<BasketballPlayer> atlantaHawks = new Team<>("Atlanta Hawks");
        chicagoBulls.addPlayer(mJordan);
        bostonCeltics.addPlayer(lJames);
        atlantaHawks.addPlayer(aDavis);

        Team<SoccerPlayer> galatasaray = new Team<>("Galatasaray");
        Team<SoccerPlayer> fenerbahce = new Team<>("Fenerbahçe");
        Team<SoccerPlayer> besiktas = new Team<>("Beşiktaş");
        galatasaray.addPlayer(hagi);
        fenerbahce.addPlayer(banu);
        besiktas.addPlayer(sergen);

        for (int i = 0; i < Team.objectCount; i++){
            if (i != 0)
                System.out.println();

            bostonRedSox.matchResult(chicagoCubs, Library.generateRandomValues(), Library.generateRandomValues());
            bostonRedSox.matchResult(chicagoWhiteSox, Library.generateRandomValues(), Library.generateRandomValues());
            System.out.println();
            chicagoCubs.matchResult(bostonRedSox, Library.generateRandomValues(), Library.generateRandomValues());
            chicagoCubs.matchResult(chicagoWhiteSox, Library.generateRandomValues(), Library.generateRandomValues());
            System.out.println();
            chicagoWhiteSox.matchResult(bostonRedSox, Library.generateRandomValues(), Library.generateRandomValues());
            chicagoWhiteSox.matchResult(chicagoCubs, Library.generateRandomValues(), Library.generateRandomValues());
            System.out.println();
            chicagoBulls.matchResult(bostonCeltics, Library.generateRandomValues(), Library.generateRandomValues());
            chicagoBulls.matchResult(atlantaHawks, Library.generateRandomValues(), Library.generateRandomValues());
            System.out.println();
            bostonCeltics.matchResult(chicagoBulls, Library.generateRandomValues(), Library.generateRandomValues());
            bostonCeltics.matchResult(atlantaHawks, Library.generateRandomValues(), Library.generateRandomValues());
            System.out.println();
            atlantaHawks.matchResult(chicagoBulls, Library.generateRandomValues(), Library.generateRandomValues());
            atlantaHawks.matchResult(bostonCeltics, Library.generateRandomValues(), Library.generateRandomValues());
            System.out.println();
            galatasaray.matchResult(fenerbahce, Library.generateRandomValues(), Library.generateRandomValues());
            galatasaray.matchResult(besiktas, Library.generateRandomValues(), Library.generateRandomValues());
            System.out.println();
            fenerbahce.matchResult(galatasaray, Library.generateRandomValues(), Library.generateRandomValues());
            fenerbahce.matchResult(besiktas, Library.generateRandomValues(), Library.generateRandomValues());
            System.out.println();
            besiktas.matchResult(galatasaray, Library.generateRandomValues(), Library.generateRandomValues());
            besiktas.matchResult(fenerbahce, Library.generateRandomValues(), Library.generateRandomValues());
        }

        System.out.println("**********************************");
        soccerLeague.addLeague(galatasaray);
        soccerLeague.addLeague(fenerbahce);
        soccerLeague.addLeague(besiktas);
        System.out.println();
        soccerLeague.showLeagueTable();
/*
        //-----------------------------------------------------------------
        BaseballPlayer pat = new BaseballPlayer("Pat", 28);
        BasketballPlayer jordan = new BasketballPlayer("M. Jordan", 50);
        Team rawTeam = new Team("Raw Team");
        rawTeam.addPlayer(jordan); // unchecked warning
        rawTeam.addPlayer(pat);     // unchecked warning

        soccerLeague.addLeague(rawTeam);     // unchecked warning

        League<Team> rawLeague = new League<>("Raw");
        rawLeague.addLeague(chicagoBulls);     // no warning
        rawLeague.addLeague(bostonCeltics);    // no warning
        rawLeague.addLeague(rawTeam);         // no warning

        League reallyRaw = new League("Really raw");
        reallyRaw.addLeague(chicagoCubs);     // unchecked warning
        reallyRaw.addLeague(bostonCeltics);    // unchecked warning
        reallyRaw.addLeague(rawTeam);           // unchecked warning
 */
    }
}
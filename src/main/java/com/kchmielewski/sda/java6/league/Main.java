package com.kchmielewski.sda.java6.league;

import com.kchmielewski.sda.java6.league.model.Leaderboard;
import com.kchmielewski.sda.java6.league.reader.MatchReader;
import com.kchmielewski.sda.java6.league.reader.StreamTeamReader;
import com.kchmielewski.sda.java6.league.reader.TeamReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        Stream<Path> teams = Files.list(Paths.get("src/main/resources/teams"));
        Stream<Path> matches = Files.list(Paths.get("src/main/resources/matches"));
        Leaderboard premierLeague = new Leaderboard();

        TeamReader teamReader = new StreamTeamReader();
        teams.map(p -> {
            try {
                return teamReader.read(p.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).filter(Objects::nonNull).forEach(premierLeague::addTeam);

        MatchReader matchReader = new MatchReader();
        matches.map(p -> {
            try {
                return matchReader.read(p.toString(), premierLeague.getTeams());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).filter(Objects::nonNull).forEach(premierLeague::addMatch);

        System.out.println(premierLeague.getGoals());
        System.out.println(premierLeague.describe());
    }
}

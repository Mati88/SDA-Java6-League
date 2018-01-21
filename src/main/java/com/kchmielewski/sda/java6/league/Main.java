package com.kchmielewski.sda.java6.league;

import com.kchmielewski.sda.java6.league.model.Leaderboard;
import com.kchmielewski.sda.java6.league.model.Match;
import com.kchmielewski.sda.java6.league.model.Team;
import com.kchmielewski.sda.java6.league.reader.MatchReader;
import com.kchmielewski.sda.java6.league.reader.StreamTeamReader;
import com.kchmielewski.sda.java6.league.reader.TeamReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        Leaderboard premierLeague = new Leaderboard();
        String teams = "src/main/resources/teams";
        String matches = "src/main/resources/matches";
        String processed = "src/main/resources/processed";
        String errors = "src/main/resources/error";

        service.scheduleWithFixedDelay(teamReader(premierLeague, teams, processed, errors), 0, 1, TimeUnit.SECONDS);
        service.scheduleWithFixedDelay(matchReader(premierLeague, matches, processed, errors), 0, 1, TimeUnit.SECONDS);
        service.scheduleWithFixedDelay(() -> {
            System.out.println("-------------------------------");
            premierLeague.describe().forEach(System.out::println);
            System.out.println("*******************************");
        }, 0, 1, TimeUnit.SECONDS);
    }

    private static Runnable teamReader(Leaderboard leaderboard, String teams, String processed, String errors) {
        TeamReader teamReader = new StreamTeamReader();
        return () -> {
            try {
                Path initialPath = Paths.get(teams);
                Stream<Path> teamPaths = Files.list(initialPath);
                teamPaths.map(p -> {
                    try {
                        Team team = teamReader.read(p.toString());
                        System.out.println(String.format("Successfully read team %s from file %s", p.getFileName().toString(), p));
                        Files.move(p, Paths.get(processed + "/" + p.getFileName().toString()));
                        return team;
                    } catch (Exception e) {
                        System.out.println("There was a problem reading team: " + p);
                        e.printStackTrace();
                        try {
                            Files.move(p, Paths.get(errors + "/" + p.getFileName().toString()));
                        } catch (IOException e1) {
                            System.out.println(String.format("Could not move %s to %s.", p, initialPath));
                            e1.printStackTrace();
                        }
                        return null;
                    }
                }).filter(Objects::nonNull).forEach(leaderboard::addTeam);
            } catch (IOException e) {
                System.out.println(String.format("There was a problem reading team directory %s. ", teams));
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private static Runnable matchReader(Leaderboard leaderboard, String matches, String processed, String errors) {
        MatchReader matchReader = new MatchReader();
        return () -> {
            try {
                Path initialPath = Paths.get(matches);
                Stream<Path> teamPaths = Files.list(initialPath);
                teamPaths.map(p -> {
                    try {
                        Match match = matchReader.read(p.toString(), leaderboard.getTeams());
                        System.out.println(String.format("Successfully read match %s from file %s", p.getFileName().toString(), p));
                        Files.move(p, Paths.get(processed + "/" + p.getFileName().toString()));
                        return match;
                    } catch (Exception e) {
                        System.out.println("There was a problem reading team: " + p);
                        e.printStackTrace();
                        try {
                            Files.move(p, Paths.get(errors + "/" + p.getFileName().toString()));
                        } catch (IOException e1) {
                            System.out.println(String.format("Could not move %s to %s.", p, initialPath));
                            e1.printStackTrace();
                        }
                        return null;
                    }
                }).filter(Objects::nonNull).forEach(leaderboard::addMatch);
            } catch (IOException e) {
                System.out.println(String.format("There was a problem reading matches directory %s. ", matches));
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}

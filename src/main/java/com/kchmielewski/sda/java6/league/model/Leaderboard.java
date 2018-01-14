package com.kchmielewski.sda.java6.league.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

public class Leaderboard {
    private final Map<String, Team> teams = new HashMap<>();
    private final Map<Team, LeaderboardResult> results = new HashMap<>();

    public void addTeam(Team team) {
        checkArgument(!teams.containsKey(team.getName()), "Cannot add %s team again.", team.getName());
        teams.put(team.getName(), team);
        results.put(team, new LeaderboardResult(team.getName()));
    }

    public void addMatch(Match match) {
        updateResults(match);
    }

    public List<LeaderboardResult> describe() {
        return results.values().stream().sorted().collect(Collectors.toList());
    }

    private TeamResult determineResult(Team team, Match match) {
        if (match.getResult() == Match.Result.DRAW) {
            return TeamResult.DRAW;
        }
        if ((match.getResult() == Match.Result.HOST && match.getHost().equals(team))
                || (match.getResult() == Match.Result.GUEST && match.getGuest().equals(team))) {
            return TeamResult.WIN;
        }

        return TeamResult.LOSE;
    }

    private void updateResults(Match match) {
        TeamResult host = determineResult(match.getHost(), match);
        TeamResult guest = determineResult(match.getGuest(), match);
        LeaderboardResult hostLeaderboardResult = results.get(match.getHost());
        LeaderboardResult guestLeaderboardResult = results.get(match.getGuest());
        results.put(match.getHost(), hostLeaderboardResult.from(host, match.getHostScores().size(), match.getGuestScores().size()));
        results.put(match.getGuest(), guestLeaderboardResult.from(guest, match.getGuestScores().size(), match.getHostScores().size()));
    }

    private enum TeamResult {
        WIN, DRAW, LOSE
    }

    public static class LeaderboardResult implements Comparable<LeaderboardResult> {
        private final String teamName;
        private final int wins;
        private final int loses;
        private final int draws;
        private final int goals;
        private final int goalsLost;
        private final int points;

        LeaderboardResult(String teamName) {
            this(teamName, 0, 0, 0, 0, 0);
        }

        LeaderboardResult(String teamName, int wins, int loses, int draws, int goals, int goalsLost) {
            this.teamName = teamName;
            this.wins = wins;
            this.loses = loses;
            this.draws = draws;
            this.goals = goals;
            this.goalsLost = goalsLost;
            points = wins * 3 + draws;
        }

        LeaderboardResult from(TeamResult teamResult, int goals, int goalsLost) {
            int newWins = teamResult == TeamResult.WIN ? wins + 1 : wins;
            int newLoses = teamResult == TeamResult.LOSE ? loses + 1 : loses;
            int newDraws = teamResult == TeamResult.DRAW ? draws + 1 : draws;
            return new LeaderboardResult(teamName, newWins, newLoses, newDraws, this.goals + goals, this.goalsLost + goalsLost);
        }

        public String getTeamName() {
            return teamName;
        }

        public int getWins() {
            return wins;
        }

        public int getLoses() {
            return loses;
        }

        public int getDraws() {
            return draws;
        }

        public int getGoals() {
            return goals;
        }

        public int getGoalsLost() {
            return goalsLost;
        }

        public int getPoints() {
            return points;
        }

        @Override
        public String toString() {
            return String.format("%s, %s (%s-%s-%s), %s/%s", teamName, points, wins, draws, loses, goals, goalsLost);
        }

        @Override
        public int compareTo(LeaderboardResult o) {
            int result = o.points - points;
            if (result == 0) {
                result = (o.goals - o.goalsLost) - (goals - goalsLost);
            }
            if (result == 0) {
                result = o.teamName.compareTo(teamName);
            }
            return result;
        }
    }
}

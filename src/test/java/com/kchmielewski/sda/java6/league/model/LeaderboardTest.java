package com.kchmielewski.sda.java6.league.model;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LeaderboardTest {
    private final Leaderboard leaderboard = new Leaderboard();
    private final Player firstPlayer = new Player("Name1", "Surname1");
    private final Player secondPlayer = new Player("Name2", "Surname2");
    private final Team firstTeam = new Team("Team F.C.", Collections.singleton(firstPlayer));
    private final Team secondTeam = new Team("A.C. Team", Collections.singleton(secondPlayer));

    @Test
    public void cannotAddSameTeamTwice() {
        leaderboard.addTeam(firstTeam);
        assertThatThrownBy(() -> leaderboard.addTeam(firstTeam)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void describeForTwoTeamsShowsWinnerFirst() {
        leaderboard.addTeam(firstTeam);
        leaderboard.addTeam(secondTeam);
        leaderboard.addMatch(new Match(firstTeam, Arrays.asList(firstPlayer, firstPlayer), secondTeam, Collections.singletonList(secondPlayer)));

        List<Leaderboard.LeaderboardResult> result = leaderboard.describe();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result.size()).isEqualTo(2);
        softly.assertThat(result.get(0).getTeamName()).isEqualTo(firstTeam.getName());
        softly.assertThat(result.get(0).getWins()).isEqualTo(1);
        softly.assertThat(result.get(0).getLoses()).isEqualTo(0);
        softly.assertThat(result.get(0).getDraws()).isEqualTo(0);
        softly.assertThat(result.get(0).getGoals()).isEqualTo(2);
        softly.assertThat(result.get(0).getGoalsLost()).isEqualTo(1);
        softly.assertThat(result.get(0).getPoints()).isEqualTo(3);
        softly.assertAll();
    }

    @Test
    public void anotherMatchChangesResult() {
        leaderboard.addTeam(firstTeam);
        leaderboard.addTeam(secondTeam);
        leaderboard.addMatch(new Match(firstTeam, Arrays.asList(firstPlayer, firstPlayer), secondTeam, Collections.singletonList(secondPlayer)));
        leaderboard.addMatch(new Match(firstTeam, Collections.singletonList(firstPlayer), secondTeam, Collections.singletonList(secondPlayer)));

        List<Leaderboard.LeaderboardResult> result = leaderboard.describe();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result.size()).isEqualTo(2);
        softly.assertThat(result.get(0).getTeamName()).isEqualTo(firstTeam.getName());
        softly.assertThat(result.get(0).getWins()).isEqualTo(1);
        softly.assertThat(result.get(0).getLoses()).isEqualTo(0);
        softly.assertThat(result.get(0).getDraws()).isEqualTo(1);
        softly.assertThat(result.get(0).getGoals()).isEqualTo(3);
        softly.assertThat(result.get(0).getGoalsLost()).isEqualTo(2);
        softly.assertThat(result.get(0).getPoints()).isEqualTo(4);
        softly.assertAll();
    }

    @Test
    public void anotherMatchChangesGoalsStatistics() {
        leaderboard.addTeam(firstTeam);
        leaderboard.addTeam(secondTeam);
        leaderboard.addMatch(new Match(firstTeam, Arrays.asList(firstPlayer, firstPlayer), secondTeam, Collections.singletonList(secondPlayer)));
        leaderboard.addMatch(new Match(firstTeam, Collections.singletonList(firstPlayer), secondTeam, Collections.singletonList(secondPlayer)));

        Map<Player, Long> result = leaderboard.getGoals();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result.size()).isEqualTo(2);
        softly.assertThat(result.get(firstPlayer)).isEqualTo(3);
        softly.assertThat(result.get(secondPlayer)).isEqualTo(2);
        softly.assertAll();
    }
}
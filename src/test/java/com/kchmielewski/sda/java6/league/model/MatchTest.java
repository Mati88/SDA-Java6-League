package com.kchmielewski.sda.java6.league.model;

import com.google.common.testing.NullPointerTester;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MatchTest {
    private final Team emptyTeam = new Team("NO_NAME");
    private final Team host = new Team("Host");
    private final Team guest = new Team("Guest");
    private final Player hostPlayer = new Player("Name1", "Surname1");
    private final Player guestPlayer = new Player("Name2", "Surname2");

    @Test
    public void nullChecks() {
        new NullPointerTester().setDefault(Team.class, emptyTeam).testAllPublicConstructors(Match.class);
    }

    @Test
    public void forNoScoresResultIsDraw() {
        Match match = new Match(host, Collections.emptyList(), guest, Collections.emptyList());

        assertThat(match.getResult()).isEqualByComparingTo(Match.Result.DRAW);
    }

    @Test
    public void forSameScoresResultIsDraw() {
        host.addPlayer(hostPlayer);
        guest.addPlayer(guestPlayer);
        Match match = new Match(host, Collections.singletonList(hostPlayer), guest, Collections.singletonList(guestPlayer));

        assertThat(match.getResult()).isEqualByComparingTo(Match.Result.DRAW);
    }

    @Test
    public void forMoreHostScoresHostWins() {
        host.addPlayer(hostPlayer);
        Match match = new Match(host, Collections.singletonList(hostPlayer), guest, Collections.emptyList());

        assertThat(match.getResult()).isEqualByComparingTo(Match.Result.HOST);
    }

    @Test
    public void forMoreGuestScoresGuestWins() {
        guest.addPlayer(guestPlayer);
        Match match = new Match(host, Collections.emptyList(), guest, Collections.singletonList(guestPlayer));

        assertThat(match.getResult()).isEqualByComparingTo(Match.Result.GUEST);
    }

    @Test
    public void forHostScoreWhichIsNotInHostPlayersThrowsException() {
        host.addPlayer(hostPlayer);

        assertThatThrownBy(() -> new Match(host, Collections.singletonList(guestPlayer), guest, Collections.emptyList())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void forGuestScoreWhichIsNotInHostPlayersThrowsException() {
        guest.addPlayer(guestPlayer);

        assertThatThrownBy(() -> new Match(host, Collections.emptyList(), guest, Collections.singletonList(hostPlayer))).isInstanceOf(IllegalArgumentException.class);
    }
}
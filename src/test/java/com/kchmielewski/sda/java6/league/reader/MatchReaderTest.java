package com.kchmielewski.sda.java6.league.reader;

import com.google.common.collect.ImmutableMap;
import com.kchmielewski.sda.java6.league.model.Match;
import com.kchmielewski.sda.java6.league.model.Player;
import com.kchmielewski.sda.java6.league.model.Team;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MatchReaderTest {
    private final MatchReader reader = new MatchReader();
    private final Map<String, Team> premierLeague;

    public MatchReaderTest() {
        Team liverpool = new Team("Liverpool F.C.");
        Team manchester = new Team("Manchester United");
        premierLeague = ImmutableMap.of(liverpool.getName(), liverpool, manchester.getName(), manchester);
        liverpool.addPlayer(new Player("Adam", "Lallana"));
        manchester.addPlayer(new Player("Zlatan", "Ibrahimović"));
    }

    @Test
    public void forNullPathThrowsException() {
        assertThatThrownBy(() -> reader.read(null, Collections.emptyMap()));
    }

    @Test
    public void forNullTeamsThrowsException() {
        assertThatThrownBy(() -> reader.read("", null));
    }

    @Test
    public void forEmptyMapThrowsException() {
        assertThatThrownBy(() -> reader.read("", Collections.emptyMap()));
    }

    @Test
    public void ifNoScoreIsPresentMatchResultIsDraw() throws IOException {
        Match match = reader.read("src/test/resources/Liverpool F.C. draws with Manchester United 0:0.txt", premierLeague);

        assertThat(match.getResult()).isEqualTo(Match.Result.DRAW);
    }

    @Test
    public void ifHostHasHigherScoreHostWins() throws IOException {
        Match match = reader.read("src/test/resources/Liverpool F.C. defeats Manchester United 3:0.txt", premierLeague);

        assertThat(match.getResult()).isEqualTo(Match.Result.HOST);
    }

    @Test
    public void ifGuestHasHigherScoreGuestWins() throws IOException {
        Match match = reader.read("src/test/resources/Liverpool F.C. is defeated by Manchester United 1:0.txt", premierLeague);

        assertThat(match.getResult()).isEqualTo(Match.Result.GUEST);
    }
}
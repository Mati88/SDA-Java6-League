package com.kchmielewski.sda.java6.league.reader;

import com.kchmielewski.sda.java6.league.model.Player;
import com.kchmielewski.sda.java6.league.model.Team;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class AbstractTeamReaderTest {
    private final TeamReader teamReader;

    AbstractTeamReaderTest(TeamReader teamReader) {
        this.teamReader = teamReader;
    }

    @Test
    public void forNullPathThrowsException() {
        assertThatThrownBy(() -> teamReader.read(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void forNonExistingFileThrowsException() {
        assertThatThrownBy(() -> teamReader.read("this file definitely does not exist")).isInstanceOf(IOException.class);
    }

    @Test
    public void teamNameIsEqualToFileName() throws IOException {
        String fileName = "src/test/resources/Arsenal F.C..txt";

        Team team = teamReader.read(fileName);

        assertThat(team.getName()).isEqualTo("Arsenal F.C.");
    }

    @Test
    public void forEmptyFileReturnsTeamWithNoPlayers() throws IOException {
        String fileName = "src/test/resources/Arsenal F.C..txt";

        Team team = teamReader.read(fileName);

        assertThat(team.getPlayers()).isEmpty();
    }

    @Test
    public void forFileWithTwoPlayersAllOfThemAreAdded() throws IOException {
        String fileName = "src/test/resources/Liverpool F.C..txt";

        Team team = teamReader.read(fileName);

        assertThat(team.getPlayers()).containsExactly(new Player("Adam", "Lallana"), new Player("Jordan", "Henderson"));
    }
}
package com.kchmielewski.sda.java6.league.model;

import org.junit.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TeamTest {

    @Test
    public void forNullNameThrowsException() {
        assertThatThrownBy(() -> new Team(null, new HashSet<>()));
    }

    @Test
    public void forEmptyNameThrowsException() {
        assertThatThrownBy(() -> new Team("", new HashSet<>()));
    }

    @Test
    public void forNullPlayersThrowsException() {
        assertThatThrownBy(() -> new Team("Name", null));
    }


    @Test
    public void addingPlayerAssignsHimToTeam() {
        Team team = new Team("Name");

        team.addPlayer(new Player("Name", "Surname"));

        assertThat(team.getPlayers()).containsExactly(new Player("Name", "Surname"));
    }

    @Test
    public void addingDifferentPLayersAssignsBothOfThem() {
        Team team = new Team("Name");

        team.addPlayer(new Player("Name1", "Surname2"));
        team.addPlayer(new Player("Name2", "Surname2"));

        assertThat(team.getPlayers()).containsExactly(new Player("Name1", "Surname2"), new Player("Name2", "Surname2"));
    }

    @Test
    public void addingSamePLayerTwiceAssignsOnlyOne() {
        Team team = new Team("Name");

        team.addPlayer(new Player("Name", "Surname"));
        team.addPlayer(new Player("Name", "Surname"));

        assertThat(team.getPlayers()).containsExactly(new Player("Name", "Surname"));
    }


}
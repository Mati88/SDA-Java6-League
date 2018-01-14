package com.kchmielewski.sda.java6.league.model;

import java.util.*;

public class Team {
    private final String name;
    private final Set<Player> players;

    public Team(String name, Collection<Player> players) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(String.format("Name cannot be empty, but %s given.", name));
        }
        if (players == null) {
            throw new IllegalArgumentException("Players cannot be null.");
        }
        this.name = name;
        this.players = new HashSet<>(players);
    }

    public Team(String name) {
        this(name, Collections.emptySet());
    }

    public String getName() {
        return name;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

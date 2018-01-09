package com.kchmielewski.sda.java6.league.reader;

import com.kchmielewski.sda.java6.league.model.Player;
import com.kchmielewski.sda.java6.league.model.Team;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StreamTeamReader implements TeamReader {
    @Override
    public Team read(String path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null.");
        }
        File file = new File(path);

        Team team = new Team(file.getName().replace(".txt", ""));
        Files.lines(Paths.get(path))
                .map(l -> l.split(";"))
                .map(s -> new Player(s[0], s[1]))
                .forEach(team::addPlayer);

        return team;
    }
}

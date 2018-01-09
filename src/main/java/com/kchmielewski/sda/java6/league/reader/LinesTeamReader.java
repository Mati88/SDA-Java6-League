package com.kchmielewski.sda.java6.league.reader;

import com.kchmielewski.sda.java6.league.model.Player;
import com.kchmielewski.sda.java6.league.model.Team;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LinesTeamReader implements TeamReader {
    @Override
    public Team read(String path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null.");
        }
        File file = new File(path);

        Team team = new Team(file.getName().replace(".txt", ""));
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (String line : lines) {
            String[] playerData = line.split(";");
            team.addPlayer(new Player(playerData[0], playerData[1]));
        }

        return team;
    }
}

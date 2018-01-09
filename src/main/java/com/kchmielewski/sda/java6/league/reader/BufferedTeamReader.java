package com.kchmielewski.sda.java6.league.reader;

import com.kchmielewski.sda.java6.league.model.Player;
import com.kchmielewski.sda.java6.league.model.Team;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BufferedTeamReader implements TeamReader {
    @Override
    public Team read(String path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null.");
        }
        File file = new File(path);

        Team team = new Team(file.getName().replace(".txt", ""));
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                String[] playerData = line.split(";");
                team.addPlayer(new Player(playerData[0], playerData[1]));
                line = reader.readLine();
            }
        }

        return team;
    }
}

package com.kchmielewski.sda.java6.league.reader;

import com.kchmielewski.sda.java6.league.model.Player;
import com.kchmielewski.sda.java6.league.model.Team;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BufferedTeamReader extends AbstractTeamReader {
    @Override
    public Team read(String path) throws IOException {
        File file = getFile(path);
        Team team = createTeam(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                team.addPlayer(Player.fromLine(line));
                line = reader.readLine();
            }
        }

        return team;
    }
}

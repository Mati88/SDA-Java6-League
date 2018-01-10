package com.kchmielewski.sda.java6.league.reader;

import com.kchmielewski.sda.java6.league.model.Team;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LinesTeamReader extends AbstractTeamReader {
    @Override
    public Team read(String path) throws IOException {
        File file = getFile(path);
        Team team = createTeam(file);
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (String line : lines) {
            team.addPlayer(lineToPlayer(line));
        }

        return team;
    }
}

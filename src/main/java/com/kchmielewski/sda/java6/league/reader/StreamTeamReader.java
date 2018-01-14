package com.kchmielewski.sda.java6.league.reader;

import com.kchmielewski.sda.java6.league.model.Player;
import com.kchmielewski.sda.java6.league.model.Team;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StreamTeamReader extends AbstractTeamReader {
    @Override
    public Team read(String path) throws IOException {
        File file = getFile(path);
        Team team = createTeam(file);
        Files.lines(Paths.get(path)).map(Player::fromLine).forEach(team::addPlayer);

        return team;
    }
}

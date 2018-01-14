package com.kchmielewski.sda.java6.league.reader;

import com.kchmielewski.sda.java6.league.model.Match;
import com.kchmielewski.sda.java6.league.model.Player;
import com.kchmielewski.sda.java6.league.model.Team;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class MatchReader {
    public Match read(String path, Map<String, Team> teams) throws IOException {
        checkNotNull(path, "Path cannot be null.");
        checkNotNull(teams, "Teams cannot be null.");
        checkArgument(teams.size() >= 2, "There must be at least two teams to read matches");

        List<String> lines = Files.readAllLines(Paths.get(path));
        String hostName = determineTeamName(lines.stream(), "host:");
        String guestName = determineTeamName(lines.stream(), "guest:");
        List<Player> hostScores = determineScores(lines.stream(), "host-score:");
        List<Player> guestScores = determineScores(lines.stream(), "guest-score:");

        return new Match(teams.get(hostName), hostScores, teams.get(guestName), guestScores);
    }

    private String determineTeamName(Stream<String> lines, String prefix) {
        return lines.filter(p -> p.startsWith(prefix))
                .map(l -> l.replace(prefix, ""))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    private List<Player> determineScores(Stream<String> lines, String prefix) {
        return lines.filter(p -> p.startsWith(prefix))
                .map(l -> l.replace(prefix, ""))
                .map(Player::fromLine)
                .collect(Collectors.toList());
    }

}

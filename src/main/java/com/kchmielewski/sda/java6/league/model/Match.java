package com.kchmielewski.sda.java6.league.model;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Match {
    private final Team host;
    private final List<Player> hostScores;
    private final Team guest;
    private final List<Player> guestScores;
    private final Result result;

    public Match(Team host, List<Player> hostScores, Team guest, List<Player> guestScores) {
        this.host = checkNotNull(host, "host == null");
        this.hostScores = checkNotNull(hostScores, "hostScores == null");
        checkArgument(host.getPlayers().containsAll(hostScores), "Host players %s\nshould contain all of %s", host.getPlayers(), hostScores);

        this.guest = checkNotNull(guest, "guest == null");
        this.guestScores = checkNotNull(guestScores, "guestScores == null");
        checkArgument(guest.getPlayers().containsAll(getGuestScores()), "Guest players\n%s should contain all of %s", guest.getPlayers(), guestScores);

        result = hostScores.size() == guestScores.size() ? Result.DRAW
                : (hostScores.size() > guestScores.size() ? Result.HOST : Result.GUEST);
    }

    public Team getHost() {
        return host;
    }

    public List<Player> getHostScores() {
        return hostScores;
    }

    public Team getGuest() {
        return guest;
    }

    public List<Player> getGuestScores() {
        return guestScores;
    }

    public Result getResult() {
        return result;
    }

    public enum Result {
        HOST, GUEST, DRAW
    }
}

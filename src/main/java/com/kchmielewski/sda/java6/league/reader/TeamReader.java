package com.kchmielewski.sda.java6.league.reader;

import com.kchmielewski.sda.java6.league.model.Team;

import java.io.IOException;

public interface TeamReader {
    Team read(String path) throws IOException;
}

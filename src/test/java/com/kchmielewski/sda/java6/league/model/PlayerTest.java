package com.kchmielewski.sda.java6.league.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PlayerTest {

    @Test
    public void forNullNameThrowsException() {
        assertThatThrownBy(() -> new Player(null, "Surname"));
    }

    @Test
    public void forEmptyNameThrowsException() {
        assertThatThrownBy(() -> new Player("", "Surname"));
    }

    @Test
    public void forNullSurnameThrowsException() {
        assertThatThrownBy(() -> new Player("Name", null));
    }

    @Test
    public void forEmptySurnameThrowsException() {
        assertThatThrownBy(() -> new Player("Name", ""));
    }

    @Test
    public void forNonEmptyNameAndSurnameDoNotThrowException() {
        new Player("Name", "Surname");
    }
}
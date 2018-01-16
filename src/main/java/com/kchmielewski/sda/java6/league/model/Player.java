package com.kchmielewski.sda.java6.league.model;

import java.util.Objects;

public class Player {
    private final String name;
    private final String surname;

    public Player(String name, String surname) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(String.format("Name cannot be empty, but %s given.", name));
        }
        if (surname == null || surname.isEmpty()) {
            throw new IllegalArgumentException(String.format("Surname cannot be empty, but %s given.", surname));
        }
        this.name = name;
        this.surname = surname;
    }

    public static Player fromLine(String line) {
        String[] data = line.split(";");
        return new Player(data[0], data[1]);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) &&
                Objects.equals(surname, player.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }
}

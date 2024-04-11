package model;

import constants.Constants;

import java.util.Date;
import java.util.Objects;

public abstract class Person {
    // Name can change, but birthday cannot
    private String name;
    private final long birthDayTimestamp;

    public Person(String name, Date birthDay) {
        this.name = Objects.requireNonNull(name);
        this.birthDayTimestamp = Objects.requireNonNull(birthDay).getTime();
    }

    public String getName() {
        return name;
    }

    public Date getBirthDay() {
        return new Date(birthDayTimestamp);
    }

    public long getBirthDayTimestamp() {
        return birthDayTimestamp;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public String toString() {
        int yearsOld = (int) ((System.currentTimeMillis() - birthDayTimestamp) / Constants.millisInYear);
        return "'" + name + "', " + yearsOld + " years old";
    }
}

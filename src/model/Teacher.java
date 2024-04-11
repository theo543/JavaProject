package model;

import java.util.Date;

public class Teacher extends Person {
    private long salaryInCents;

    public Teacher(String name, Date birthDay, long salaryInCents) {
        super(name, birthDay);
        this.salaryInCents = salaryInCents;
    }
    public long getSalaryInCents() {
        return salaryInCents;
    }
    public void setSalaryInCents(long salaryInCents) {
        this.salaryInCents = salaryInCents;
    }
    @Override
    public String toString() {
        String personDescription = super.toString();
        return personDescription + " - teacher with salary " + salaryInCents / 100 + " euros";
    }
}

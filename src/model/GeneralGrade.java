package model;

public class GeneralGrade extends Grade {
    private final double grade;
    public GeneralGrade(double grade) {
        if (grade < 0 || grade > 10) {
            throw new IllegalArgumentException("Grades are between 0 and 10");
        }
        this.grade = grade;
    }

    @Override
    public double getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Grade: " + grade + " / 10";
    }
}

package model;

public class GeneralGrade extends Grade {
    private final double grade;
    public GeneralGrade(Student student, Course course, double grade) {
        super(student, course);
        if (grade < 0 || grade > 10) {
            throw new IllegalArgumentException("Grades are between 1 and 10.");
        }
        if(grade < 1) {
            throw new IllegalArgumentException("Only FailGrades can be 0/10.");
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
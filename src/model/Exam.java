package model;

public class Exam {
    private final String name;
    private final Course course;
    private int totalGrades;
    private double gradesSum;
    private int fails;
    public Exam(String name, Course course) {
        this.name = name;
        this.course = course;
        this.totalGrades = 0;
        this.gradesSum = 0;
        this.fails = 0;
    }
    public String getName() {
        return name;
    }
    public Course getCourse() {
        return course;
    }
    public String toString() {
        return "Exam " + name + " for course " + course;
    }
    public void recordGrade(Grade grade) {
        if (grade.getGrade() == 0) {
            fails++;
        }
        totalGrades++;
        gradesSum += grade.getGrade();
    }
    public ExamStats getStats() {
        return new ExamStats(totalGrades, gradesSum / totalGrades, fails);
    }
}

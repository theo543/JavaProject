package model;

public class Exam {
    private final String name;
    private final Course course;
    public Exam(String name, Course course) {
        this.name = name;
        this.course = course;
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
}

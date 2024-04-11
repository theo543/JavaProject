package model;

public abstract class Grade {
    private final long timestamp;
    private final Student student;
    private final Course course;
    public Grade(Student student, Course course) {
        this.timestamp = System.currentTimeMillis();
        this.student = student;
        this.course = course;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public Student getStudent() {
        return student;
    }
    public Course getCourse() {
        return course;
    }
    abstract public double getGrade();
    abstract public String toString();
}

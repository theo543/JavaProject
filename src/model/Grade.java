package model;

public abstract class Grade {
    private final long timestamp;
    private final Student student;
    private final Exam exam;
    public Grade(Student student, Exam exam) {
        this.timestamp = System.currentTimeMillis();
        this.student = student;
        this.exam = exam;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public Student getStudent() {
        return student;
    }
    public Exam getExam() {
        return exam;
    }
    abstract public double getGrade();
    abstract public String toString();
}

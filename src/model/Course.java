package model;

import java.util.Objects;

public class Course {
    // The course will always be the same, but the teacher and classroom can change
    private final String name;
    private Teacher teacher;
    private Classroom classroom;
    private double gradeSum;
    private int gradeCount;
    private int failCount;
    private int examCount;
    public Course(Teacher teacher, String name, Classroom classroom) {
        this.teacher = Objects.requireNonNull(teacher);
        this.name = Objects.requireNonNull(name);
        this.classroom = Objects.requireNonNull(classroom);
        this.gradeSum = 0;
        this.gradeCount = 0;
        this.failCount = 0;
        this.examCount = 0;
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = Objects.requireNonNull(teacher);
    }
    public String getName() {
        return name;
    }
    public Classroom getClassroom() {
        return classroom;
    }
    public void setClassroom(Classroom classroom) {
        this.classroom = Objects.requireNonNull(classroom);
    }
    public String toString() {
        return "Course " + name + " with teacher " + teacher + " in classroom " + classroom;
    }
    public void recordGrade(Grade grade) {
        if (grade.getGrade() == 0) {
            failCount++;
        }
        gradeCount++;
        gradeSum += grade.getGrade();
    }
    public void recordExam(Exam exam) {
        // exam not used, but might be needed in the future
        examCount++;
    }
    public CourseStats getStats() {
        return new CourseStats(gradeCount, examCount, gradeSum / gradeCount, failCount);
    }
}

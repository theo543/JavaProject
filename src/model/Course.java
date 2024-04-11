package model;

import java.util.Objects;

public class Course {
    // The course will always be the same, but the teacher and classroom can change
    private final String name;
    private Teacher teacher;
    private Classroom classroom;
    public Course(Teacher teacher, String name, Classroom classroom) {
        this.teacher = Objects.requireNonNull(teacher);
        this.name = Objects.requireNonNull(name);
        this.classroom = Objects.requireNonNull(classroom);
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
}

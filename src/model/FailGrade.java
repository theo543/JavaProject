package model;

/*
    Unconditional failure due to exceptional issues, which cause immediate failure.
*/
public class FailGrade extends Grade {
    private final FailCause cause;
    public FailGrade(Student student, Course course, FailCause cause) {
        super(student, course);
        this.cause = cause;
    }

    @Override
    public double getGrade() {
        return 0;
    }

    @Override
    public String toString() {
        return "Unconditional failure due to " + cause;
    }
}

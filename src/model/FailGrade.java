package model;

/*
    Unconditional failure due to exceptional issues, which cause immediate failure.
*/
public class FailGrade extends Grade {
    private final FailCause cause;
    public FailGrade(Student student, Exam exam, FailCause cause) {
        super(student, exam);
        this.cause = cause;
    }

    @Override
    public double getGrade() {
        return 0;
    }

    @Override
    public String toString() {
        return "Unconditional failure due to " + cause +
                ", course: " + getExam().getCourse().getName() +
                ", exam: " + getExam().getName() +
                " (considered as 0/10)";
    }
}

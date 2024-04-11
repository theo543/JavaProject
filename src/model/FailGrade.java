package model;

/*
    Unconditional failure due to exceptional issues, which cause immediate failure.
*/
public class FailGrade extends Grade {
    public enum FailCause {
        ABSENCE,
        CHEATING,
        DISRUPTION,
        VIOLENCE,
        OTHER
    }
    private final FailCause cause;
    public FailGrade(FailCause cause) {
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

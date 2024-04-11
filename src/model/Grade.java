package model;

public abstract class Grade {
    private final long timestamp;
    public Grade() {
        this.timestamp = System.currentTimeMillis();
    }
    public long getTimestamp() {
        return timestamp;
    }
    abstract public double getGrade();
    abstract public String toString();
}

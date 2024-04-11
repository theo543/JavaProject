package model;

import java.util.Date;
import java.util.Objects;

public class Classroom {
    private final String roomId;
    private final int capacity;
    private long safetyInspectionTimestamp;
    public Classroom(String roomId, int capacity, Date safetyInspection) {
        this.roomId = Objects.requireNonNull(roomId);
        this.capacity = capacity;
        this.safetyInspectionTimestamp = Objects.requireNonNull(safetyInspection).getTime();
    }
    public String getRoomId() {
        return roomId;
    }
    public int getCapacity() {
        return capacity;
    }
    public Date getSafetyInspection() {
        return new Date(safetyInspectionTimestamp);
    }
    public void setSafetyInspection(Date safetyInspection) {
        if (safetyInspection.getTime() > System.currentTimeMillis()) {
            throw new IllegalArgumentException("Safety inspection cannot be in the future");
        }
        if (safetyInspection.getTime() < safetyInspectionTimestamp) {
            throw new IllegalArgumentException("Latest safety inspection cannot be earlier than the previous one");
        }
        this.safetyInspectionTimestamp = safetyInspection.getTime();
    }
    public String toString() {
        int daysSinceInspection = (int) ((System.currentTimeMillis() - safetyInspectionTimestamp) / (1000 * 60 * 60 * 24));
        return "Room " + roomId + " with capacity " + capacity;
    }
}

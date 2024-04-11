package model;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class Student extends Person {
    // Used in grades, since name can change
    private final long studentUID;
    private String emergencyPhoneContact;
    // Grant is optional
    private Grant grant;
    public Student(String name, Date birthDay, String emergencyPhoneContact, Grant grant) {
        super(name, birthDay);
        this.emergencyPhoneContact = Objects.requireNonNull(emergencyPhoneContact);
        this.grant = grant;
        this.studentUID = new Random().nextLong();
    }
    public Student(String name, Date birthDay, String emergencyPhoneContact) {
        this(name, birthDay, emergencyPhoneContact, null);
    }
    public void setEmergencyPhoneContact(String emergencyPhoneContact) {
        this.emergencyPhoneContact = Objects.requireNonNull(emergencyPhoneContact);
    }
    public String getEmergencyPhoneContact() {
        return emergencyPhoneContact;
    }
    public void setGrant(Grant grant) {
        this.grant = grant;
    }
    public Grant getGrant() {
        return grant;
    }
    public long getUID() {
        return studentUID;
    }
    @Override
    public String toString() {
        String personDescription = super.toString();
        String studentDescription = personDescription + " - student with emergency contact " + emergencyPhoneContact;
        if (grant != null) {
            return studentDescription + ", has grant: " + grant;
        }
        return studentDescription;
    }
}

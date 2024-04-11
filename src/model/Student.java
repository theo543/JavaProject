package model;

import java.util.Date;
import java.util.Objects;

public class Student extends Person {
    private String emergencyPhoneContact;
    // Grant is optional
    private Grant grant;
    public Student(String name, Date birthDay, String emergencyPhoneContact, Grant grant) {
        super(name, birthDay);
        this.emergencyPhoneContact = Objects.requireNonNull(emergencyPhoneContact);
        this.grant = grant;
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

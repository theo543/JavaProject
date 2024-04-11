package model;

import constants.Constants;

import java.util.Objects;

public class Grant {
    // Amount cannot change by government regulation
    private final long amountInCents;
    private String description;
    public Grant(long amountInCents, String description) {
        this.amountInCents = amountInCents;
        this.description = Objects.requireNonNull(description);
    }
    public long getAmountInCents() {
        return amountInCents;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "Grant of " + amountInCents / Constants.centsInEuro + " euros: " + description;
    }
}

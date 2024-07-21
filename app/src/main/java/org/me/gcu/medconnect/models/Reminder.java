package org.me.gcu.medconnect.models;

public class Reminder {
    private String medicationName;
    private String dosage;
    private String frequency;
    private String timeOfDay;
    private String nextReminderTime;

    public Reminder(String medicationName, String dosage, String frequency, String timeOfDay, String nextReminderTime) {
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.timeOfDay = timeOfDay;
        this.nextReminderTime = nextReminderTime;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getNextReminderTime() {
        return nextReminderTime;
    }

    public void setNextReminderTime(String nextReminderTime) {
        this.nextReminderTime = nextReminderTime;
    }
}


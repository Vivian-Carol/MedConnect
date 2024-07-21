package org.me.gcu.medconnect.models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBDocument;

@DynamoDBDocument
public class Medication {

    private String medicationName;
    private String milligrams;
    private String price;

    public Medication() {}

    public Medication(String medicationName, String milligrams, String price) {
        this.medicationName = medicationName;
        this.milligrams = milligrams;
        this.price = price;
    }

    @DynamoDBAttribute(attributeName = "MedicationName")
    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    @DynamoDBAttribute(attributeName = "Milligrams")
    public String getMilligrams() {
        return milligrams;
    }

    public void setMilligrams(String milligrams) {
        this.milligrams = milligrams;
    }

    @DynamoDBAttribute(attributeName = "Price")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "medicationName='" + medicationName + '\'' +
                ", milligrams='" + milligrams + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}

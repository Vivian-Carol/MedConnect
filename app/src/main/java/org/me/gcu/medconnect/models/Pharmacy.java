package org.me.gcu.medconnect.models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = "Pharmacies")
public class Pharmacy {

    private String pharmacyID;
    private String pharmacyName;
    private String pharmacyPhoneNumber;
    private String pharmacyAddress;
    private String town;
    private List<Medication> medications;

    public Pharmacy() {}

    public Pharmacy(String pharmacyID, String pharmacyName, String pharmacyPhoneNumber, String pharmacyAddress, String town, List<Medication> medications) {
        this.pharmacyID = pharmacyID;
        this.pharmacyName = pharmacyName;
        this.pharmacyPhoneNumber = pharmacyPhoneNumber;
        this.pharmacyAddress = pharmacyAddress;
        this.town = town;
        this.medications = medications;
    }

    @DynamoDBHashKey(attributeName = "PharmacyID")
    public String getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(String pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    @DynamoDBAttribute(attributeName = "PharmacyName")
    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    @DynamoDBAttribute(attributeName = "PharmacyPhoneNumber")
    public String getPharmacyPhoneNumber() {
        return pharmacyPhoneNumber;
    }

    public void setPharmacyPhoneNumber(String pharmacyPhoneNumber) {
        this.pharmacyPhoneNumber = pharmacyPhoneNumber;
    }

    @DynamoDBAttribute(attributeName = "PharmacyAddress")
    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    @DynamoDBAttribute(attributeName = "Town")
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @DynamoDBAttribute(attributeName = "Medications")
    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }
}

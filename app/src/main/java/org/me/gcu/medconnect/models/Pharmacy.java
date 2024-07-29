//package org.me.gcu.medconnect.models;
//
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
//
//import java.util.List;
//
//@DynamoDBTable(tableName = "Pharmacies")
//public class Pharmacy {
//
//    private String pharmacyID;
//    private String pharmacyName;
//    private String pharmacyPhoneNumber;
//    private String pharmacyAddress;
//    private String town;
//    private List<Medication> medications;
//
//    public Pharmacy() {}
//
//    public Pharmacy(String pharmacyID, String pharmacyName, String pharmacyPhoneNumber, String pharmacyAddress, String town, List<Medication> medications) {
//        this.pharmacyID = pharmacyID;
//        this.pharmacyName = pharmacyName;
//        this.pharmacyPhoneNumber = pharmacyPhoneNumber;
//        this.pharmacyAddress = pharmacyAddress;
//        this.town = town;
//        this.medications = medications;
//    }
//
//    @DynamoDBHashKey(attributeName = "PharmacyID")
//    public String getPharmacyID() {
//        return pharmacyID;
//    }
//
//    public void setPharmacyID(String pharmacyID) {
//        this.pharmacyID = pharmacyID;
//    }
//
//    @DynamoDBAttribute(attributeName = "PharmacyName")
//    public String getPharmacyName() {
//        return pharmacyName;
//    }
//
//    public void setPharmacyName(String pharmacyName) {
//        this.pharmacyName = pharmacyName;
//    }
//
//    @DynamoDBAttribute(attributeName = "PharmacyPhoneNumber")
//    public String getPharmacyPhoneNumber() {
//        return pharmacyPhoneNumber;
//    }
//
//    public void setPharmacyPhoneNumber(String pharmacyPhoneNumber) {
//        this.pharmacyPhoneNumber = pharmacyPhoneNumber;
//    }
//
//    @DynamoDBAttribute(attributeName = "PharmacyAddress")
//    public String getPharmacyAddress() {
//        return pharmacyAddress;
//    }
//
//    public void setPharmacyAddress(String pharmacyAddress) {
//        this.pharmacyAddress = pharmacyAddress;
//    }
//
//    @DynamoDBAttribute(attributeName = "Town")
//    public String getTown() {
//        return town;
//    }
//
//    public void setTown(String town) {
//        this.town = town;
//    }
//
//    @DynamoDBAttribute(attributeName = "Medications")
//    public List<Medication> getMedications() {
//        return medications;
//    }
//
//    public void setMedications(List<Medication> medications) {
//        this.medications = medications;
//    }
//}
package org.me.gcu.medconnect.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName = "Pharmacies")
public class Pharmacy implements Parcelable {

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

    protected Pharmacy(Parcel in) {
        pharmacyID = in.readString();
        pharmacyName = in.readString();
        pharmacyPhoneNumber = in.readString();
        pharmacyAddress = in.readString();
        town = in.readString();
        if (in.readByte() == 0x01) {
            medications = new ArrayList<>();
            in.readList(medications, Medication.class.getClassLoader());
        } else {
            medications = null;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pharmacyID);
        dest.writeString(pharmacyName);
        dest.writeString(pharmacyPhoneNumber);
        dest.writeString(pharmacyAddress);
        dest.writeString(town);
        if (medications == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(medications);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Pharmacy> CREATOR = new Parcelable.Creator<Pharmacy>() {
        @Override
        public Pharmacy createFromParcel(Parcel in) {
            return new Pharmacy(in);
        }

        @Override
        public Pharmacy[] newArray(int size) {
            return new Pharmacy[size];
        }
    };
}

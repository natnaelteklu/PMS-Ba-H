package com.pms.dto;

import java.util.List;

public class FacilityDto {
    private int facilityId;
    private String companyName;
    private String contactNumber;
    private List<ParkingSpaceDto> parkingSpaces;

    // Getters and Setters
    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<ParkingSpaceDto> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpaceDto> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }
}

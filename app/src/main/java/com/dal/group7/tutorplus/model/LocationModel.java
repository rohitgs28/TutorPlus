package com.dal.group7.tutorplus.model;

import java.io.Serializable;

public class LocationModel implements Serializable {
    private int locationId;
    private String locationName;

    public LocationModel() {
    }

    public LocationModel(int locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}

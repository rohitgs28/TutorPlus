package com.dal.group7.tutorplus.model;

import java.io.Serializable;

public class TimeScheduleModel implements Serializable {
    private String timing;
    private boolean availability;

    public TimeScheduleModel(String timing, boolean availability) {
        this.timing = timing;
        this.availability = availability;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}

package com.dal.group7.tutorplus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ScheduleModel implements Serializable {
    // Days are unique and every day is mapped to a list of Timing Model
    private Map<String,ArrayList<TimeScheduleModel>> dayTimingMap;

    public ScheduleModel(Map<String, ArrayList<TimeScheduleModel>> dayTimingMap) {
        this.dayTimingMap = dayTimingMap;
    }

    public Map<String, ArrayList<TimeScheduleModel>> getDayTimingMap() {
        return dayTimingMap;
    }

    public void setDayTimingMap(Map<String, ArrayList<TimeScheduleModel>> dayTimingMap) {
        this.dayTimingMap = dayTimingMap;
    }
}

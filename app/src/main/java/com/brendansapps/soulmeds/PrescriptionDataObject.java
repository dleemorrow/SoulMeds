package com.brendansapps.soulmeds;

/**
 * Created by bt on 03/27/18.
 *
 * Datatype consisting of a symptom/time name, a boolean, and an ID
 *
 * Utilized by PrescriptionManager & PrescriptionAlarmManager
 */

// Object for representing either a symptom or a time for the Alarm prescriptions
public class PrescriptionDataObject {
    public String name;
    public Boolean isActive;
    public int alarmID;
}

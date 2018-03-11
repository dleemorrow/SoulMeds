package com.brendansapps.soulmeds;

import java.sql.Time;

/**
 * Created by bt on 3/11/18.
 *
 * Class created to manage the current prescriptions for the user
 *
 * Utilized in the AlarmsActivity
 *
 */

public class PrescriptionObject {

    public String symptom;
    public String alarmTime;
    public Boolean isActive;

    public PrescriptionObject(String symptom, String alarmTime){
        this.symptom = symptom;
        this.alarmTime = alarmTime;
        this.isActive = true;
    }

    public String getSymptom(){
        return this.symptom;
    }

    public String getAlarmTime(){
        return this.alarmTime;
    }

    public Boolean getActiveStatus(){
        return this.isActive;
    }

}

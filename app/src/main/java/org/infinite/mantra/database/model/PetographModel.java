package org.infinite.mantra.database.model;

public class PetographModel {
    private String userHeight;
    private String userWeight;
    private String dateMeasured;
    private String timeMeasured;
    private String diastolic;
    private String lnmp;
    private String pulseRate;
    private String systolic;
    private String bmi;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(String userHeight) {
        this.userHeight = userHeight;
    }

    public String getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(String userWeight) {
        this.userWeight = userWeight;
    }

    public String getDateMeasured() {
        return dateMeasured;
    }

    public void setDateMeasured(String dateMeasured) {
        this.dateMeasured = dateMeasured;
    }

    public String getTimeMeasured() {
        return timeMeasured;
    }

    public void setTimeMeasured(String timeMeasured) {
        this.timeMeasured = timeMeasured;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getLnmp() {
        return lnmp;
    }

    public void setLnmp(String lnmp) {
        this.lnmp = lnmp;
    }

    public String getPulseRate() {
        return pulseRate;
    }

    public void setPulseRate(String pulseRate) {
        this.pulseRate = pulseRate;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }
}

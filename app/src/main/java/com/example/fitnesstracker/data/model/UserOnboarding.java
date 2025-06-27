package com.example.fitnesstracker.data.model;

public class UserOnboarding {
    private String experience;
    private String frequency;
    private String runningGoal;
    private String trainingEvent;

    public UserOnboarding(String experience, String frequency, String runningGoal, String trainingEvent) {
        this.experience = experience;
        this.frequency = frequency;
        this.runningGoal = runningGoal;
        this.trainingEvent = trainingEvent;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getRunningGoal() {
        return runningGoal;
    }

    public void setRunningGoal(String runningGoal) {
        this.runningGoal = runningGoal;
    }

    public String getTrainingEvent() {
        return trainingEvent;
    }

    public void setTrainingEvent(String trainingEvent) {
        this.trainingEvent = trainingEvent;
    }
}

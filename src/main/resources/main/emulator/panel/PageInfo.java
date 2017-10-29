package main.emulator.panel;

public class PageInfo {
    private Integer currentFloor;
    private Integer lastFloor;
    private Integer groundFloor;
    private String personConditionMessage;
    private Integer personId;
    private String errorMessage;

    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(Integer currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Integer getLastFloor() {
        return lastFloor;
    }

    public void setLastFloor(Integer lastFloor) {
        this.lastFloor = lastFloor;
    }

    public Integer getGroundFloor() {
        return groundFloor;
    }

    public void setGroundFloor(Integer groundFloor) {
        this.groundFloor = groundFloor;
    }

    public String getPersonConditionMessage() {
        return personConditionMessage;
    }

    public void setPersonConditionMessage(String personConditionMessage) {
        this.personConditionMessage = personConditionMessage;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

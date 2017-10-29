package main.emulator.panel.contract;

public class ServerInfo {
    private Integer currentFloor;
    private Integer lastFloor;
    private Integer groundFloor;

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
}

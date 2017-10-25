package main.entities;

import java.util.Arrays;

public class ElevatorCondition {
    private Integer lastStopFloor = null;
    private Integer currentFloor = null;
    private final Boolean[] callPoints;
    private final Boolean[] sendPoints;
    private Integer direction = 0;
    private final Integer lastFloorNumber;

    public ElevatorCondition(Building building) {
        lastFloorNumber = building.getLastFloor();
        callPoints = new Boolean[building.getFloorCount()];
        Arrays.fill(callPoints, false);
        sendPoints = new Boolean[building.getFloorCount()];
        Arrays.fill(sendPoints, false);
        this.currentFloor = building.getGroundFloor();
    }

    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public void callup(Integer floor) {
        callPoints[floor] = true;
        if (lastStopFloor == null) {
            setLastFloor();
        }
    }

    public void send(Integer floor) {
        if (currentFloor.equals(floor)) {
            return;
        }
        sendPoints[floor] = true;
        if (lastStopFloor == null) {
            setLastFloor();
        }
    }

    Boolean stopNextFloor() {
        Integer nextFloor = currentFloor + direction;
        return sendPoints[nextFloor] || callPoints[nextFloor];
    }

    void stop() throws InterruptedException {
        callPoints[currentFloor] = false;
        sendPoints[currentFloor] = false;
        if (currentFloor.equals(lastStopFloor) || lastStopFloor == null || lastStopFloor == -1) {
            setLastFloor();
        }
    }

    void changeCurrentFloor() {
        if (!direction.equals(0)) {
            currentFloor += direction;
        }
    }

    Boolean canMove() {
        return canMoveUp() || canMoveDown();
    }

    private Boolean canMoveUp() {
        return direction > 0 && !currentFloor.equals(lastFloorNumber);
    }

    private Boolean canMoveDown() {
        return direction < 0 && !currentFloor.equals(0);
    }

    private void setLastFloor() {
        Boolean value = true;
        if (direction >= 0) {
            lastStopFloor = Arrays.asList(sendPoints).lastIndexOf(value);
            if (lastStopFloor == -1) {
                lastStopFloor = Arrays.asList(callPoints).lastIndexOf(value);
            }
        } else {
            lastStopFloor = Arrays.asList(sendPoints).indexOf(value);
            if (lastStopFloor == -1) {
                lastStopFloor = Arrays.asList(callPoints).indexOf(value);
            }
        }
        changeDirection();
    }

    private void changeDirection() {
        if (currentFloor.equals(lastStopFloor) || lastStopFloor == null || lastStopFloor == -1) {
            lastStopFloor = null;
            direction = 0;
        } else if (lastStopFloor - currentFloor < 0) {
            direction = -1;
        } else {
            direction = 1;
        }
    }
}

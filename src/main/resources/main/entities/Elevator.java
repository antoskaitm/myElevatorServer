package main.entities;

import java.util.Arrays;

public class Elevator {
    private Integer lastFloor = null;
    private Integer currentFloor = null;
    private final Boolean[] callPoints;
    private final Boolean[] sendPoints;

    private final Integer speed;
    private final Integer acceleration;
    private final Building building;

    private Integer direction = 0;


    public Elevator(Building building, int speed, int acceleration) {
        callPoints = new Boolean[building.getLastFloor()];
        Arrays.fill(callPoints,false);
        sendPoints = new Boolean[building.getLastFloor()];
        Arrays.fill(sendPoints,false);
        this.building = building;

        this.speed = speed;
        this.acceleration = acceleration;
        this.currentFloor = building.getGroundFloor();
    }

    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public void callup(Integer floor) {
        callPoints[floor] = true;
        if (lastFloor == null) {
            setLastFloor();
        }
    }

    public void send(Integer floor) {
        if (currentFloor.equals(floor)) {
            throw new IllegalStateException("This is current floor");
        }
        sendPoints[floor] = true;
        if (lastFloor == null) {
            setLastFloor();
        }
    }

    private void stop() {
        //ожидание//Thread.sleep(500);
        callPoints[currentFloor] = false;
        sendPoints[currentFloor] = false;
    }

    private void move() {
        /*Integer path = building.getFloorHeight();
        Integer accelerationTime = speed / acceleration;
        Integer stopTime = accelerationTime;
        if (path / 2 < acceleration * accelerationTime * accelerationTime / 2) {
            stopTime = accelerationTime / 2;
        }
        Integer stopPath = stopTime * stopTime * acceleration / 2;
        Integer constantSpeedPath = path - 2 * stopPath;
        //ускорились//Thread.sleep(stopTime);
        Boolean isPathLong = true;
        if (!stopNextFloor() && stopTime != accelerationTime) {
            //доускорились////Thread.sleep(accelerationTime-stopTime);
            isPathLong = false;
            stopTime = accelerationTime;
            stopPath = stopTime * stopTime * acceleration / 2;
            constantSpeedPath = path - stopPath;
        }
        Integer constantSpeedTime = constantSpeedPath / speed;
        //постоянное////Thread.sleep(constantSpeedTime);
        Integer moveTime = stopPath / speed;
        while (!stopNextFloor()) {
            //движемся с постоянной участок остановки//Thread.sleep(moveTime);
            changeCurrentFloor();
            if (isPathLong) {
                //движемся с постоянной участок ускорения//Thread.sleep(moveTime);
            }
            //движемся с постоянной//Thread.sleep(constantSpeedTime);
            if (currentFloor == 0 || currentFloor == building.getLastFloor()) {
                break;
            }
        }
        //остановка//Thread.sleep(stopTime);
        */

        while ((canMoveDown() || canMoveUp()) && !stopNextFloor()) {
            changeCurrentFloor();
        }
        if(canMoveDown() || canMoveUp()) {
            changeCurrentFloor();
        }
    }

    private void changeCurrentFloor() {
        // building.checkFloor(currentFloor + direction);
        if (!direction.equals(0)) {
            currentFloor += direction;
        }
    }

    private Boolean canMoveUp() {
        return direction > 0 && !currentFloor.equals(building.getLastFloor());
    }

    private Boolean canMoveDown() {
        return direction < 0 && !currentFloor.equals(building.getGroundFloor());
    }

    private Boolean suspend = false;

    public void run() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!suspend) {
                        move();
                        stop();
                        if (currentFloor.equals(lastFloor) || lastFloor == null || lastFloor == -1) {
                            setLastFloor();
                        }
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void setLastFloor() {
        Boolean value = true;
        if (direction >= 0) {
            lastFloor = Arrays.asList(sendPoints).lastIndexOf(value);
            if (lastFloor == -1) {
                lastFloor = Arrays.asList(callPoints).lastIndexOf(value);
            }
        } else {
            lastFloor = Arrays.asList(sendPoints).indexOf(value);
            if (lastFloor == -1) {
                lastFloor = Arrays.asList(callPoints).indexOf(value);
            }
        }
        changeDirection();
    }

    private Boolean stopNextFloor() {
        Integer nextFloor = currentFloor + direction;
        return sendPoints[nextFloor] || callPoints[nextFloor];
    }

    private void changeDirection() {
        if (currentFloor.equals(lastFloor) || lastFloor == null || lastFloor == -1) {
            lastFloor = null;
            direction = 0;
        } else if (lastFloor - currentFloor < 0) {
            direction = -1;
        } else {
            direction = 1;
        }
    }
}

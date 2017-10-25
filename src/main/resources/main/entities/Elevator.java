package main.entities;


public class Elevator {
    private Integer lastFloor=null;
    private Integer currentFloor = 0;
    private final Boolean[] callPoints;
    private final Boolean[] sendPoints;

    private Integer speed=1;
    private final Integer acceleration=2;
    private Integer direction = 1;

    private final Building building;

    public Elevator(Building building)
    {
        callPoints = new Boolean[building.getLastFloor()];
        sendPoints = new Boolean[building.getLastFloor()];
        this.building = building;

    }

    public void callup(Integer floor)
    {
        building.checkFloor(floor);
        callPoints[floor] = true;
    }

    public void send(Integer floor)
    {
        building.checkFloor(floor);
        sendPoints[floor] = true;
    }

    public Integer getCurrentFloor()
    {
        return currentFloor;
    }

    private void stop()
    {

        callPoints[currentFloor] = false;
        sendPoints[currentFloor] = false;
        changeDirection();
        move();
    }

    private void move() {
        Integer path = building.getFloorHeight();
        Integer accelerationTime = speed / acceleration;
        Integer stopTime = accelerationTime;
        if (path / 2 < acceleration * accelerationTime * accelerationTime) {
            stopTime = accelerationTime / 2;
        }
        Integer stopPath = stopTime * stopTime * acceleration / 2;
        Integer constantSpeedPath = path - 2 * stopPath;
        Integer constantSpeedTime = constantSpeedPath / speed;
        //ускорились
        //постоянное
        Boolean isPathLong = true;
        if (!stopNextFloor() && constantSpeedTime == 0) {
            //доускорились
            isPathLong = false;
            stopTime = accelerationTime;
            stopPath = stopTime * stopTime * acceleration / 2;
            constantSpeedPath = path - stopPath;
            constantSpeedTime = constantSpeedPath / speed;
        }
        Integer moveTime = stopPath / speed;
        while (!stopNextFloor()) {
            //движемся с постоянной участок остановки
            currentFloor += direction;
            if (isPathLong) {
                //движемся с постоянной участок ускорения
            }
            //движемся с постоянной
            if(currentFloor == 0 || currentFloor == building.getLastFloor())
            {
                break;
            }
        }
        //остановка
        stop();
    }

    private Boolean stopNextFloor()
    {
        Integer nextFloor = currentFloor+direction;
        return sendPoints[nextFloor] || callPoints[nextFloor];
    }

    private void changeDirection()
    {
        for (int floor = currentFloor;floor>0 && floor < sendPoints.length;floor += direction)
        {
            if(sendPoints[floor])
            {
                return;
            }
        }
        direction *= -1;
    }
}

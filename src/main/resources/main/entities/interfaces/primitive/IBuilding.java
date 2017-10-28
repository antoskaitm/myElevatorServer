package main.entities.interfaces.primitive;

import main.entities.interfaces.IAutomobileElevatorRoom;

public interface IBuilding {

    Boolean hasFloor(int floor);

    Integer getLastFloor();

    Integer getFloorCount();

    Integer getFloorHeight();

    Integer getGroundFloor();

    IAutomobileElevatorRoom getElevator(Integer elevatorNumber);
}

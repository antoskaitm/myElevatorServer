package main.entities;

public interface IBuilding {

    void checkFloor(int floor);

    Boolean hasFloor(int floor);

    Integer getLastFloor();

    Integer getFloorCount();

    Integer getFloorHeight();

    Integer getGroundFloor();
}

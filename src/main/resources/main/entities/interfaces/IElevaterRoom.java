package main.entities.interfaces;

public interface IElevaterRoom {
    Integer callElevator(int floor);

    Integer getCurrentFloor();

    Boolean SendElevator(int floor, int personId);
}

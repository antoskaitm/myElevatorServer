package main.entities.interfaces;

import main.entities.PersonCondition;

public interface IElevatorRoom {
    Integer callElevator(int floor);

    Integer getCurrentFloor();

    Boolean sendElevator(int floor, int personId);

    boolean isInElevator(Integer personId);

    boolean isCallElevator(Integer personId);

    boolean isSendElevator(Integer personId);

    PersonCondition getPersonCondition(Integer personId);
}
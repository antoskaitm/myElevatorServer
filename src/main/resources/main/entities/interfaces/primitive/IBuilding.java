package main.entities.interfaces.primitive;

import main.entities.interfaces.IAutomobileElevatorRoom;

public interface IBuilding {

	IFloorsRange getFloorRange();

	Integer getFloorsRange();

	IAutomobileElevatorRoom getElevator(Integer elevatorNumber);
}

package main.entities.interfaces.primitive;

import main.entities.interfaces.IAutomobileElevatorRoom;

public interface IBuilding extends IFloorRanged {

	IFloorsRange getFloorsRange();

	Integer getFloorHeight(Integer floor);

	IAutomobileElevatorRoom getElevator(Integer elevatorNumber);
}

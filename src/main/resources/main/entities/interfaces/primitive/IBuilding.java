package main.entities.interfaces.primitive;

import main.entities.interfaces.IAutomobileElevatorRoom;

public interface IBuilding<T extends IRequesting> extends IFloorRanged {

	IFloorsRange getFloorsRange();

	Integer getFloorHeight(Integer floor);

	IAutomobileElevatorRoom<T> getElevator(Integer elevatorNumber);
}

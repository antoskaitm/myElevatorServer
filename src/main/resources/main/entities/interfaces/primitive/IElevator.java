package main.entities.interfaces.primitive;

import main.entities.primitive.Request;

public interface IElevator extends IFloorRanged  {
	Request callElevator(int floor);

	Integer getCurrentFloor();

	Boolean sendElevator(int floor, int requestId);

	boolean isInElevator(Integer requestId);

	boolean isCallElevator(Integer requestId);

	boolean isSendElevator(Integer requestId);

	IConditionable getRequestCondition(Integer requestId);
}
package main.entities.interfaces.primitive;

public interface IElevator extends IFloorRanged  {
	Integer callElevator(int floor);

	Integer getCurrentFloor();

	Boolean sendElevator(int floor, int requestId);

	boolean isInElevator(Integer requestId);

	boolean isCallElevator(Integer requestId);

	boolean isSendElevator(Integer requestId);

	IConditionable getRequestCondition(Integer requestId);
}
package main.entities.interfaces.primitive;

public interface IElevatorRoom {
	Integer callElevator(int floor);

	Integer getCurrentFloor();

	Boolean sendElevator(int floor, int requestId);

	boolean isInElevator(Integer requestId);

	boolean isCallElevator(Integer requestId);

	boolean isSendElevator(Integer requestId);

	IConditionable getPersonCondition(Integer requestId);

	IFloorsRange getFloorsRange();
}
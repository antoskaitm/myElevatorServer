package main.entities.interfaces.primitive;

public interface IElevatorRoom {
	Integer callElevator(int floor);

	Integer getCurrentFloor();

	Boolean sendElevator(int floor, int personId);

	boolean isInElevator(Integer personId);

	boolean isCallElevator(Integer personId);

	boolean isSendElevator(Integer personId);

	IConditionable getPersonCondition(Integer personId);
}
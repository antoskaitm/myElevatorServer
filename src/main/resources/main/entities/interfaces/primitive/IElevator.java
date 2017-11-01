package main.entities.interfaces.primitive;

public interface IElevator<Key extends IRequesting> extends IFloorRanged  {
	Boolean callElevator(int floor,Key expectant);

	Integer getCurrentFloor();

	Boolean sendElevator(int floor, Key expectant);
}
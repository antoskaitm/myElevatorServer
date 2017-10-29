package main.entities.interfaces.primitive;

public interface IFloorsRange {
	int getGroundFloor();
	int getLastFloor();
	int getFloorCount();
	void checkFloor(int floor);
	boolean hasFloor(int floor);
	boolean contains(IFloorsRange floorsRange);
}

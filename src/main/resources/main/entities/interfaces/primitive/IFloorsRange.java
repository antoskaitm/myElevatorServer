package main.entities.interfaces.primitive;

/** floor range must be immutable
 *
 */
public interface IFloorsRange {
	int getGroundFloor();
	int getLastFloor();
	int getFloorCount();
	void checkFloor(int floor);
	boolean hasFloor(int floor);
	boolean contains(IFloorsRange floorsRange);
}

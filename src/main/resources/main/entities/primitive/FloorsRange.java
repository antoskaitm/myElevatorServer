package main.entities.primitive;

import main.entities.interfaces.primitive.IFloorsRange;

import java.io.Serializable;

public class FloorsRange implements IFloorsRange, Serializable{
	private int groundFlooor;
	private int lastFloor;

	public FloorsRange(int groundFloor,int lastFloor)
	{
		init(groundFloor, lastFloor);
	}

	public FloorsRange(IFloorsRange inner, IFloorsRange outer) {
		int groundFloor = Integer.max(inner.getGroundFloor(), outer.getGroundFloor());
		int lastFloor = Integer.min(inner.getLastFloor(), outer.getLastFloor());
		init(groundFloor, lastFloor);
	}

	private void init(int groundFloor,int lastFloor)
	{
		Integer minFloorCount = 2;
		Integer floorCount = lastFloor - groundFlooor +1;
		if (floorCount < minFloorCount) {
			throw new IllegalArgumentException("Floor count must be " + minFloorCount + " or more");
		}
		else if(groundFloor>lastFloor) {
			throw new IllegalArgumentException("last floor must be mor than ground floor");
		}
		this.groundFlooor = groundFloor;
		this.lastFloor = lastFloor;
	}


	@Override
	public boolean hasFloor(int floor) {
		return groundFlooor<=floor && floor<=lastFloor;
	}

	@Override
	public int getGroundFloor() {
		return groundFlooor;
	}

	@Override
	public int getLastFloor() {
		return lastFloor;
	}

	@Override
	public int getFloorCount() {
		return lastFloor - groundFlooor +1;
	}

	@Override
	public void checkFloor(int floor) {
		if (floor < groundFlooor || floor > lastFloor) {
			throw new NullPointerException("Floor range has not floor");
		}
	}

	@Override
	public boolean contains(IFloorsRange floorsRange) {
		return groundFlooor <= floorsRange.getGroundFloor()
				&& lastFloor >= floorsRange.getLastFloor();
	}
}

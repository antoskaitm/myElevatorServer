package main.entities.primitive;

import main.entities.interfaces.IAutomobileElevatorRoom;
import main.entities.interfaces.primitive.IBuilding;
import main.entities.interfaces.primitive.IFloorsRange;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * numbers of floors begin from 0
 */
public class Building implements IBuilding, Serializable {
	static final long serialVersionUID = -3000000000000L;
	private Integer floorHeight;
	private IFloorsRange floorsRange;
	private IAutomobileElevatorRoom[] elevators;

	public Building(IFloorsRange floorsRange, int buildingHeight, IAutomobileElevatorRoom... elevators) {
		this.elevators = elevators;
		Integer floorCount = floorsRange.getFloorCount();
		Integer minFloorHeight = 3;
		if (buildingHeight < floorCount * minFloorHeight) {
			throw new IllegalArgumentException("Value of building height for lastFloor must be more than "
					+ (floorCount * minFloorHeight));
		}
		this.floorHeight = buildingHeight / floorCount;
		this.floorsRange = floorsRange;
		checkFloorsRanges(elevators);
		this.elevators = elevators;
	}

	@Override
	public IFloorsRange getFloorsRange() {
		return this.floorsRange;
	}

	@Override
	public Integer getFloorHeight(Integer floor) {
		return floorHeight;
	}

	@Override
	public IAutomobileElevatorRoom getElevator(Integer elevatorNumber) {
		return elevators[elevatorNumber];
	}

	private void checkFloorsRanges(IAutomobileElevatorRoom[] elevators) {
		for (int index = 0; index < elevators.length; index++) {
			if (!floorsRange.contains(elevators[index].getFloorsRange())) {
				throw new IllegalStateException("Elevator by index " + index + " go out of building floors range");
			}
		}
	}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		long serialVersionUID = stream.readLong();
		floorsRange = (IFloorsRange) stream.readObject();
		floorHeight = (Integer) stream.readObject();
		elevators = (IAutomobileElevatorRoom[]) stream.readObject();
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeLong(serialVersionUID);
		stream.writeObject(floorsRange);
		stream.writeObject(floorHeight);
		stream.writeObject(elevators);
	}
}

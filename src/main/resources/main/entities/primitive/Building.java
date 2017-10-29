package main.entities.primitive;

import main.entities.interfaces.IAutomobileElevatorRoom;
import main.entities.interfaces.primitive.IBuilding;
import main.entities.view.AbstractElevatorRoomView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * numbers of floors begin from 0
 */
public class Building implements IBuilding, Serializable {
	static final long serialVersionUID = -3000000000000L;
	private Integer lowerBorder;
	private Integer upperBorder;
	private Integer floorCount;
	private Integer floorHeight;

	private IAutomobileElevatorRoom[] elevators;

	public Building(int lowerBorder, int floorCount, int buildingHeight, IAutomobileElevatorRoom... elevators) {
		this.elevators = elevators;
		Integer minFloorCount = 3;
		if (floorCount < minFloorCount) {
			throw new IllegalArgumentException("Floor count must be " + minFloorCount + " or more");
		}
		Integer minFloorHeight = 3;
		if (buildingHeight < floorCount * minFloorHeight) {
			throw new IllegalArgumentException("Value of building height for lastFloor must be more than "
					+ (floorCount * minFloorHeight));
		}
		this.lowerBorder = lowerBorder;
		this.floorCount = floorCount;
		this.upperBorder = floorCount - 1 + lowerBorder;
		this.floorHeight = buildingHeight / floorCount;
		this.elevators = envelop(elevators);
	}

	private void checkFloor(int floor) {
		if (floor < lowerBorder || floor > upperBorder) {
			throw new NullPointerException("Floor does not exist");
		}
	}

	public Boolean hasFloor(int floor) {
		return floor >= lowerBorder && floor <= upperBorder;
	}

	public Integer getLastFloor() {
		return upperBorder;
	}

	public Integer getFloorCount() {
		return floorCount;
	}

	public Integer getFloorHeight() {
		return floorHeight;
	}

	public Integer getGroundFloor() {
		return lowerBorder;
	}

	@Override
	public IAutomobileElevatorRoom getElevator(Integer elevatorNumber) {
		return elevators[elevatorNumber];
	}

	private IAutomobileElevatorRoom[] envelop(IAutomobileElevatorRoom[] elevators) {
		IAutomobileElevatorRoom[] elevatorsCopy = Arrays.copyOf(elevators, elevators.length);
		Arrays.setAll(elevatorsCopy, value -> new AbstractElevatorRoomView(elevators[value]) {
			@Override
			public Integer callElevator(int floor) {
				checkFloor(floor);
				return super.callElevator(floor);
			}

			@Override
			public Boolean sendElevator(int floor, int personId) {
				checkFloor(floor);
				return super.sendElevator(floor, personId);
			}
		});
		return elevatorsCopy;
	}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		long serialVersionUID = stream.readLong();
		lowerBorder = (Integer) stream.readObject();
		upperBorder = (Integer) stream.readObject();
		floorHeight = (Integer) stream.readObject();
		floorCount=this.upperBorder+1- lowerBorder;
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeLong(serialVersionUID);
		stream.writeObject(lowerBorder);
		stream.writeObject(upperBorder);
		stream.writeObject(floorHeight);
	}
}

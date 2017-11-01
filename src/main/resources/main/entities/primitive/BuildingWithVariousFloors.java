package main.entities.primitive;

import main.entities.interfaces.IAutomobileElevatorRoom;
import main.entities.interfaces.primitive.IFloorsRange;
import main.entities.interfaces.primitive.IPersonElevator;
import main.entities.primitive.abstractclass.AbstractBuilding;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class BuildingWithVariousFloors extends AbstractBuilding {
	private static final long serialVersionUID = 0;
	private Integer[] floorsHeights;

	public BuildingWithVariousFloors(IFloorsRange floorsRange, IAutomobileElevatorRoom<Person>... elevators) {
		super(floorsRange, elevators);
		if (floorsHeights.length != floorsRange.getFloorCount()) {
			throw new IllegalArgumentException("Wrong floor count");
		}
		this.floorsHeights = Arrays.copyOf(floorsHeights, floorsHeights.length);
		checkFloorHeights();
	}

	@Override
	public Integer getFloorHeight(Integer floor) {
		return floorsHeights[floor];
	}

	private void checkFloorHeights() {
		Integer minFloorHeight = 3;
		for (Integer floorHeight : this.floorsHeights) {
			if (floorHeight < minFloorHeight) {
				throw new IllegalArgumentException("Value of floor height  must be more than "
						+ minFloorHeight);
			}
		}
	}

	@Override
	protected void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		super.readObject(stream);
		long serialVersionUID = stream.readLong();
		floorsHeights = (Integer[]) stream.readObject();
	}

	@Override
	protected void writeObject(ObjectOutputStream stream) throws IOException {
		super.writeObject(stream);
		stream.writeLong(serialVersionUID);
		stream.writeObject(floorsHeights);
	}
}

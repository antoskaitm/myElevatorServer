package main.entities.primitive;

import main.entities.interfaces.events.Action;
import main.entities.interfaces.primitive.IElevatorAutomate;
import main.entities.interfaces.primitive.IElevatorAutomateble;
import main.entities.interfaces.primitive.IElevatorUi;
import main.entities.primitive.general.BitSet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * numbers of floors begin from 0
 */
class ElevatorCondition implements IElevatorUi, IElevatorAutomateble, Serializable {
	static final long serialVersionUID = -2000000000000L;

	private Integer moveToFloor = null;
	private Integer currentFloor = null;
	private BitSet callPoints;
	private Integer direction = 0;
	private IElevatorAutomate automate;

	public ElevatorCondition(BitSet callPoints) {
		Integer minFloorCount = 2;
		if (callPoints.getSize() < minFloorCount) {
			throw new IllegalArgumentException("Floor count must be " + minFloorCount + " or more");
		}
		this.callPoints = callPoints;
		this.currentFloor = 0;
	}

	@Override
	public Integer getCurrentFloor() {
		return currentFloor;
	}

	@Override
	public Boolean callup(Integer floor) {
		if (!hasFloor(floor)) {
			return false;
		}
		callPoints.set(floor);
		if (moveToFloor == null || direction == 0) {
			setLastFloor();
		}
		return callPoints.get(floor);
	}

	public IElevatorAutomate getElevatorAutomate() {
		if (automate == null) {
			automate = new IElevatorAutomate() {
				private List<Action> actions = new ArrayList<>();

				@Override
				public Boolean stopNextFloor() {
					return callPoints.get(currentFloor + direction);
				}

				@Override
				public void stop() {
					callPoints.clear(currentFloor);
					if (currentFloor.equals(moveToFloor) || moveToFloor == null) {
						setLastFloor();
					}
					for (Action action : actions) {
						action.execute();
					}
				}

				@Override
				public void changeCurrentFloor() {
					currentFloor += direction;
				}

				@Override
				public Boolean canMove() {
					return canMoveUp() || canMoveDown();
				}

				@Override
				public void onStop(Action action) {
					this.actions.add(action);
				}

				private Boolean canMoveUp() {
					return direction > 0 && !currentFloor.equals(callPoints.getUpperBorder());
				}

				private Boolean canMoveDown() {
					return direction < 0 && !currentFloor.equals(callPoints.getLowerBorder());
				}
			};
		}
		return automate;
	}

	private Boolean hasFloor(Integer floor) {
		return floor >= callPoints.getLowerBorder() && floor <= callPoints.getUpperBorder();
	}

	private void setLastFloor() {
		this.moveToFloor = callPoints.findIndex(direction >= 0);
		changeDirection();
	}

	private void changeDirection() {
		if (currentFloor.equals(moveToFloor) || moveToFloor == null) {
			moveToFloor = null;
			direction = 0;
		} else if (moveToFloor - currentFloor < 0) {
			direction = -1;
		} else {
			direction = 1;
		}
	}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		long serialVersionUID = stream.readLong();
		moveToFloor = (Integer) stream.readObject();
		currentFloor = (Integer) stream.readObject();
		callPoints = (BitSet) stream.readObject();
		direction = (Integer) stream.readObject();
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeLong(serialVersionUID);
		stream.writeObject(moveToFloor);
		stream.writeObject(currentFloor);
		stream.writeObject(callPoints);
		stream.writeObject(direction);
	}
}
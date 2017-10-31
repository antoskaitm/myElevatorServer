package main.entities.primitive;

import main.entities.interfaces.events.Action;
import main.entities.interfaces.primitive.IAutomobileElevator;
import main.entities.interfaces.primitive.IElevatorAutomate;
import main.entities.interfaces.primitive.ICallable;
import main.entities.interfaces.primitive.IFloorsRange;
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
class ElevatorCondition implements ICallable, IAutomobileElevator, Serializable {
	static final long serialVersionUID = -2000000000000L;

	private Integer moveToFloor = null;
	private Integer currentFloor = 0;
	private BitSet callPoints;
	private Integer direction = 0;
	private IFloorsRange floorsRange;
	private IElevatorAutomate automate;

	public ElevatorCondition(BitSet callPoints, IFloorsRange floorsRange) {
		if (floorsRange.getGroundFloor() != callPoints.getLowerBorder()
				|| callPoints.getUpperBorder() != floorsRange.getLastFloor()) {
			throw new ArithmeticException("Call points has different floor range");
		}
		this.floorsRange = floorsRange;
		this.callPoints = callPoints;
		this.currentFloor = 0;
	}

	@Override
	public Integer getCurrentFloor() {
		return currentFloor;
	}

	@Override
	public Boolean call(Integer floor) {
		floorsRange.checkFloor(floor);
		callPoints.set(floor);
		if (moveToFloor == null || direction == 0) {
			setLastFloor();
		}
		return callPoints.get(floor);
	}

	@Override
	public IFloorsRange getFloorsRange() {
		return floorsRange;
	}

	public IElevatorAutomate getElevatorAutomate() {
		if (automate == null) {
			automate = new IElevatorAutomate() {

				private List<Action> actions = new ArrayList<>();

				@Override
				public Boolean stopNextFloor() {
					floorsRange.checkFloor(currentFloor + direction);
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
					floorsRange.checkFloor(currentFloor + direction);
					currentFloor += direction;
				}

				@Override
				public Boolean canMove() {
					return floorsRange.hasFloor(currentFloor + direction);
				}

				@Override
				public void onStop(Action action) {
					this.actions.add(action);
				}

				@Override
				public boolean isCalled()
				{
					return callPoints.findIndex(false)!=null;
				}

				@Override
				public Integer getCurrentFloor() {
					return currentFloor;
				}
			};
		}
		return automate;
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
		floorsRange = (IFloorsRange) stream.readObject();
		direction = (Integer) stream.readObject();
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeLong(serialVersionUID);
		stream.writeObject(moveToFloor);
		stream.writeObject(currentFloor);
		stream.writeObject(callPoints);
		stream.writeObject(floorsRange);
		stream.writeObject(direction);
	}
}
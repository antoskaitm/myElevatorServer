package main.entities.view;

import main.entities.interfaces.IAutomobileElevatorRoom;
import main.entities.interfaces.primitive.IConditionable;
import main.entities.interfaces.primitive.IElevatorAutomate;

public abstract class AbstractElevatorRoomView implements IAutomobileElevatorRoom {

	private IAutomobileElevatorRoom elevatorRoom;

	public AbstractElevatorRoomView(IAutomobileElevatorRoom elevatorRoom) {
		this.elevatorRoom = elevatorRoom;
	}

	@Override
	public IElevatorAutomate getElevatorAutomate() {
		return elevatorRoom.getElevatorAutomate();
	}

	@Override
	public Integer getCurrentFloor() {
		return elevatorRoom.getCurrentFloor();
	}

	@Override
	public boolean isInElevator(Integer personId) {
		return elevatorRoom.isInElevator(personId);
	}

	@Override
	public boolean isCallElevator(Integer personId) {
		return elevatorRoom.isCallElevator(personId);
	}

	@Override
	public boolean isSendElevator(Integer personId) {
		return elevatorRoom.isSendElevator(personId);
	}

	@Override
	public IConditionable getPersonCondition(Integer personId) {
		return elevatorRoom.getPersonCondition(personId);
	}
}

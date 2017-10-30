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
	public boolean isInElevator(Integer requestId) {
		return elevatorRoom.isInElevator(requestId);
	}

	@Override
	public boolean isCallElevator(Integer requestId) {
		return elevatorRoom.isCallElevator(requestId);
	}

	@Override
	public boolean isSendElevator(Integer requestId) {
		return elevatorRoom.isSendElevator(requestId);
	}

	@Override
	public IConditionable getRequestCondition(Integer requestId) {
		return elevatorRoom.getRequestCondition(requestId);
	}
}

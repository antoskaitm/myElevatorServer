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

	protected IAutomobileElevatorRoom getElevatorRoom(){return elevatorRoom;}
}

package main.entities;

import main.entities.interfaces.IAutomobileElevatorRoom;
import main.entities.interfaces.primitive.*;
import main.entities.primitive.Person;

import java.io.Serializable;

public class PersonElevatorRoom implements IPersonElevator, Serializable {

	private IAutomobileElevatorRoom elevatorRoom;

	public PersonElevatorRoom(IAutomobileElevatorRoom elevatorRoom)
	{
		this.elevatorRoom = elevatorRoom;
	}

	@Override
	public synchronized boolean callElevator(int floor,Person person) {
		if (person.withoutState()){
			person.request = elevatorRoom.callElevator(floor);
			return true;
		}
		return false;
	}

	@Override
	public Integer getCurrentFloor() {
		return elevatorRoom.getCurrentFloor();
	}

	@Override
	public synchronized Boolean sendElevator(int floor, Person person) {
		if (elevatorRoom.isInElevator(person.request.getId())) {
			return elevatorRoom.sendElevator(floor, person.request.getId());
		}
		return false;
	}

	@Override
	public IElevatorAutomate getElevatorAutomate() {
		return elevatorRoom.getElevatorAutomate();
	}

	@Override
	public IFloorsRange getFloorsRange() {return  elevatorRoom.getFloorsRange();}
}

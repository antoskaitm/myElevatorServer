package main.entities;

import main.entities.constants.PersonsConditions;
import main.entities.interfaces.IAutomobileElevatorRoom;
import main.entities.interfaces.primitive.*;
import main.entities.primitive.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class PersonElevatorRoom implements IPersonElevator, Serializable {

	private IAutomobileElevatorRoom elevatorRoom;

	public PersonElevatorRoom(IAutomobileElevatorRoom elevatorRoom)
	{
		this.elevatorRoom = elevatorRoom;
	}

	@Override
	public synchronized boolean callElevator(int floor,Person person) {
		if (person.request != null) {
			Request request = elevatorRoom.callElevator(floor);
			if (request != null) {
				person.request = request;
				return true;
			}
		}
		return false;
	}

	@Override
	public Integer getCurrentFloor() {
		return elevatorRoom.getCurrentFloor();
	}

	@Override
	public synchronized Boolean sendElevator(int floor, Person person) {
		if (person.request != null) {
			if (elevatorRoom.isSendElevator(person.request.getId())) {
				return elevatorRoom.sendElevator(floor, person.request.getId());
			}
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

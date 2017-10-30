package main.entities.interfaces.primitive;

import main.entities.Person;
import main.entities.interfaces.IAutomobileElevatorRoom;
import main.entities.primitive.Request;

public interface IPersonElevator extends IAutomobileElevator,IFloorRanged {
	boolean callElevator(int floor,Person person);

	Integer getCurrentFloor();

	Boolean sendElevator(int floor, Person person);
}

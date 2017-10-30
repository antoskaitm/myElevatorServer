package main.entities.interfaces.primitive;

import main.entities.primitive.Person;

public interface IPersonElevator extends IAutomobileElevator,IFloorRanged {
	boolean callElevator(int floor,Person person);

	Integer getCurrentFloor();

	Boolean sendElevator(int floor, Person person);
}

package main.entities.interfaces.primitive;

import main.entities.interfaces.events.Action;

public interface IElevatorAutomate {
	void stop();

	Boolean stopNextFloor();

	void changeCurrentFloor();

	Boolean canMove();

	void onStop(Action event);

	boolean isCalled();

	Integer getCurrentFloor();
}

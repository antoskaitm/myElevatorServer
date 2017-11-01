package main.entities.interfaces.primitive;

import main.entities.interfaces.events.Action;

public interface IElevatorAutomate {
	void stop();

	Boolean isStopNextFloor();

	void changeCurrentFloor();

	Boolean canChangeCurrentFloor();

	void onStop(Action event);

	boolean isCalled();

	Integer getCurrentFloor();
}

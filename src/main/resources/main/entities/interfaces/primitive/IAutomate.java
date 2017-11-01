package main.entities.interfaces.primitive;

import main.entities.general.events.Action;

public interface IAutomate {
	void stop();

	Boolean isStopNext();

	void changeCurrentFloor();

	Boolean canChangeCurrentFloor();

	void onStop(Action event);

	boolean isCalled();

	Integer getCurrentFloor();
}

package main.entities.interfaces.primitive;

import main.entities.primitive.ElevatorRequest;

public interface IRequesting {
	ElevatorRequest getRequest();

	void setRequest(ElevatorRequest elevatorRequest);
}

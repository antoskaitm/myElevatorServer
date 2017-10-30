package main.entities.interfaces.primitive;

public interface IElevatorController {

	Integer getCurrentFloor();

	Boolean callup(Integer floor);

	IFloorsRange getFloorsRange();
}

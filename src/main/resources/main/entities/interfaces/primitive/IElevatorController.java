package main.entities.interfaces.primitive;

public interface IElevatorController extends IFloorRanged{

	Integer getCurrentFloor();

	Boolean callup(Integer floor);
}

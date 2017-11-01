package main.entities.interfaces.primitive;

public interface ICallablePanel extends IFloorRanged{

	Integer getCurrentFloor();

	Boolean call(Integer floor);
}

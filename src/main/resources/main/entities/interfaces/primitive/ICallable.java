package main.entities.interfaces.primitive;

public interface ICallable extends IFloorRanged{

	Integer getCurrentFloor();

	Boolean call(Integer floor);
}

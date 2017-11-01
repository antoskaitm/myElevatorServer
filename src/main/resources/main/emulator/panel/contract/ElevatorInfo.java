package main.emulator.panel.contract;

import main.entities.interfaces.primitive.IElevator;
import main.entities.interfaces.primitive.IFloorsRange;

public class ElevatorInfo {
	private IElevator elevator;
	private Integer id;

	public ElevatorInfo(IElevator elevator, Integer id)
	{
		this.elevator =elevator;
		this.id = id;
	}

	public Integer getCurrentFloor() {
		return elevator.getCurrentFloor();
	}

	public Integer getLastFloor() {
		return elevator.getFloorsRange().getLastFloor();
	}

	public Integer getGroundFloor() {
		return elevator.getFloorsRange().getGroundFloor();
	}

	public Integer getId()
	{
		return id;
	}
}

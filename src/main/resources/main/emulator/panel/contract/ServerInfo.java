package main.emulator.panel.contract;

import java.io.Serializable;

public class ServerInfo implements Serializable {
	private Integer currentFloor;
	private ElevatorInfo[] elevators;

	public ElevatorInfo[] getElevators() {
		return elevators;
	}

	public void setElevatorsId(ElevatorInfo[] elevators)
	{
		this.elevators = elevators;
	}
}

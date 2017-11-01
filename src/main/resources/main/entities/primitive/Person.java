package main.entities.primitive;

import main.entities.interfaces.primitive.IRequesting;

public class Person implements IRequesting,Comparable {
	private Integer id;
	private ElevatorRequest request;

	@Override
	public ElevatorRequest getRequest() {
		return request;
	}

	@Override
	public void setRequest(ElevatorRequest elevatorRequest) {
			this.request = elevatorRequest;
	}

	@Override
	public int compareTo(Object o) {
		return request.getId()-((Person)o).getRequest().getId();
	}
}

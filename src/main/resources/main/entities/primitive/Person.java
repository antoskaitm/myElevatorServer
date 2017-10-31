package main.entities.primitive;

import main.entities.constants.RequestsConditions;

public class Person {
	private Request request;

	public boolean isInElevator() {
		return getRequest() != null && getRequest().getCondition() == RequestsConditions.STAND_IN_ELEVATOR;
	}

	public boolean isCallElevator() {
		return getRequest() != null && getRequest().getCondition() == RequestsConditions.CALLED_ELEVATOR;
	}

	public boolean isSendElevator() {
		return getRequest() != null && getRequest().getCondition() == RequestsConditions.SENDED_ELEVATOR;
	}

	public boolean withoutState() {
		return getRequest() == null
				|| getRequest().getCondition() == RequestsConditions.DIDNOT_CALL_ELEVATOR
				|| getRequest().getCondition() == RequestsConditions.TRY_CALL_AGAIN_ELEVATOR;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
}

package main.entities.primitive;

import main.entities.constants.RequestsConditions;
import main.entities.primitive.Request;

public class Person {
	public Request request;

	public boolean isInElevator() {
		return request != null && request.getCondition() == RequestsConditions.STAND_IN_ELEVATOR;
	}

	public boolean isCallElevator() {
		return request != null && request.getCondition() == RequestsConditions.CALLED_ELEVATOR;
	}

	public boolean isSendElevator() {
		return request != null && request.getCondition() == RequestsConditions.SENDED_ELEVATOR;
	}

	public boolean withoutState() {
		return request == null
				|| request.getCondition() == RequestsConditions.DIDNOT_CALL_ELEVATOR
				|| request.getCondition() == RequestsConditions.TRY_CALL_AGAIN_ELEVATOR;
	}
}

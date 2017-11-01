package main.entities.constants;

import main.entities.general.IConditionable;

public class RequestsConditions implements IConditionable {
	private final String message;

	private RequestsConditions(String message) {
		this.message = message;
	}

	public static final RequestsConditions DIDNOT_CALL_ELEVATOR = new RequestsConditions(" didn't call elevator room");
	public static final RequestsConditions TRY_CALL_AGAIN_ELEVATOR = new RequestsConditions(" must try to call elevator room again");
	public static final RequestsConditions CALLED_ELEVATOR = new RequestsConditions(" wait to into elevator room");
	public static final RequestsConditions STAND_IN_ELEVATOR = new RequestsConditions(" stand in elevator room");
	public static final RequestsConditions SENDED_ELEVATOR = new RequestsConditions(" wait to out from elevator room");

	public String getMessage() {
		return message;
	}
}

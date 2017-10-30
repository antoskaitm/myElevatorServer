package main.entities.constants;

import main.entities.interfaces.primitive.IConditionable;

public class RequestsConditions implements IConditionable {
	private final String message;

	private RequestsConditions(String message) {
		this.message = message;
	}

	public static final IConditionable DIDNOT_CALL_ELEVATOR = new RequestsConditions(" didn't call elevator room");
	public static final IConditionable TRY_CALL_AGAIN_ELEVATOR = new RequestsConditions(" must try to call elevator room again");
	public static final IConditionable CALLED_ELEVATOR = new RequestsConditions(" wait to into elevator room");
	public static final IConditionable STAND_IN_ELEVATOR = new RequestsConditions(" stand in elevator room");
	public static final IConditionable SENDED_ELEVATOR = new RequestsConditions(" wait to out from elevator room");

	public String getMessage() {
		return message;
	}
}

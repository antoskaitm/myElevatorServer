package main.entities.constants;

import main.entities.interfaces.primitive.IConditionable;

public class PersonsConditions implements IConditionable {
	private final String message;

	private PersonsConditions(String message) {
		this.message = message;
	}

	public static final IConditionable DIDNOT_CALL_ELEVATOR = new PersonsConditions("Request didn't call elevator room");
	public static final IConditionable TRY_CALL_AGAIN_ELEVATOR = new PersonsConditions("Request must try to call elevator room again");
	public static final IConditionable CALLED_ELEVATOR = new PersonsConditions("Request wait to into elevator room");
	public static final IConditionable STAND_IN_ELEVATOR = new PersonsConditions("Request stand in elevator room");
	public static final IConditionable SENDED_ELEVATOR = new PersonsConditions("Request wait to out from elevator room");

	public String getMessage() {
		return message;
	}
}

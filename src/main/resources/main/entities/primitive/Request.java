package main.entities.primitive;

import main.entities.constants.RequestsConditions;
import main.entities.interfaces.primitive.IConditionable;

public class Request {
	private Integer id;
	private IConditionable condition;
	private Integer callFloor;
	private Integer sendFloor;

	public Request(Integer id, Integer callFloor) {
		this.id = id;
		this.callFloor = callFloor;
		condition = RequestsConditions.CALLED_ELEVATOR;
	}

	public void setSendFloor(Integer sendFloor) {
		this.sendFloor = sendFloor;
	}

	public Integer getId() {
		return id;
	}

	public Integer getCallFloor() {
		return callFloor;
	}

	public Integer getSendFloor() {
		return sendFloor;
	}

	public IConditionable getCondition() {
		return condition;
	}

	public void setCondition(IConditionable newCondition) {
		if (condition == newCondition) {
			return;
		}
		if ((condition == RequestsConditions.DIDNOT_CALL_ELEVATOR || condition == RequestsConditions.TRY_CALL_AGAIN_ELEVATOR)
				&& newCondition == RequestsConditions.CALLED_ELEVATOR) {
			condition = newCondition;
		} else if (condition == RequestsConditions.CALLED_ELEVATOR
				&& newCondition == RequestsConditions.STAND_IN_ELEVATOR) {
			condition = newCondition;
		} else if (condition == RequestsConditions.CALLED_ELEVATOR
				&& newCondition == RequestsConditions.TRY_CALL_AGAIN_ELEVATOR) {
			condition = newCondition;
		} else if (condition == RequestsConditions.STAND_IN_ELEVATOR
				&& newCondition == RequestsConditions.SENDED_ELEVATOR) {
			condition = newCondition;
		} else if (condition == RequestsConditions.SENDED_ELEVATOR
				&& newCondition == RequestsConditions.DIDNOT_CALL_ELEVATOR) {
			condition = newCondition;
		} else {
			throw new IllegalStateException("Incorrect new state:" + newCondition.getMessage() + ". Current state: " + condition.getMessage());
		}
	}
}

package main.emulator.panel.contract;

import java.io.Serializable;

public class PersonInfo implements Serializable {
	private String personConditionMessage;
	private Integer currentFloor;
	private Integer requestId;
	private String errorMessage;

	public String getPersonConditionMessage() {
		return personConditionMessage;
	}

	public void setPersonConditionMessage(String personConditionMessage) {
		this.personConditionMessage = personConditionMessage;
	}

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}

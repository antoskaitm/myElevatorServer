package main.entities.interfaces.primitive;

public interface IRequestStatus {
	Integer getId();

	Integer getCallFloor();

	Integer getSendFloor();

	boolean isInElevator();

	boolean isCallElevator();

	boolean isSendElevator();

	boolean withoutState();
}

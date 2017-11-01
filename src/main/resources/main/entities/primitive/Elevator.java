package main.entities.primitive;

import main.entities.constants.RequestsConditions;
import main.entities.interfaces.IAutomobileElevatorRoom;
import main.entities.interfaces.primitive.*;
import org.omg.CORBA.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * view for object implementing IAutomobileElevator,ICallablePanel
 * can work with basement floors
 * this class also keep information about people
 * all methods which change room state is synchronized
 *
 * @param <T>
 */
public class Elevator<T extends ICallablePanel & IAutomobileElevator & Serializable,U extends IRequesting>
		implements IAutomobileElevatorRoom<U>, Serializable {

	private static final long serialVersionUID = -1000000000000L;
	private static Integer elevatorsCount = 0;
	private Integer requestCounterId = 0;

	private T elevatorCondition;
	private Integer id;
	//не сохраняю т.к пока нет механизма чтобы выгнать из лифта если пользователь нехочет выходить
	//чтобы не испортить объетк при загрузке из хранилища
	private Set<U> expectants;
	private IRoom<U> room;

	public Elevator(T elevatorCondition, IRoom<U> room) {
		expectants = new ConcurrentSkipListSet<>();
		this.room = room;
		this.elevatorCondition = elevatorCondition;
		this.elevatorCondition.getElevatorAutomate().onStop(this::stop);
		this.id = elevatorsCount++;
	}

	@Override
	public Boolean callElevator(int callFloor, U expectant) {
		ElevatorRequest request = expectant.getRequest();
		if ((request== null || request.withoutState()) && elevatorCondition.call(callFloor)) {
			Integer requestId = requestCounterId++;
			request = new ElevatorRequest(requestId, callFloor,id);
			if (expectants.add(expectant)) {
				expectant.setRequest(request);
				return true;
			}
		}
		return false;
	}

	@Override
	public Integer getCurrentFloor() {
		return elevatorCondition.getCurrentFloor();
	}

	@Override
	public synchronized Boolean sendElevator(int floor, U expectant) {
		ElevatorRequest request = expectant.getRequest();
		if (request != null && request.isInElevator() && elevatorCondition.call(floor)) {
			if (expectants.contains(expectant)) {
				request.setSendFloor(floor);
				request.setCondition(RequestsConditions.SENDED_ELEVATOR);
				return true;
			}
			throw new IllegalArgumentException("ElevatorRequest did not found");
		}
		return false;
	}

	private synchronized void stop() {
		Integer currentFloor = elevatorCondition.getCurrentFloor();
		for (U expectant : expectants) {
			ElevatorRequest request = expectant.getRequest();
			if (currentFloor.equals(request.getSendFloor()) && request.isSendElevator()) {
				expectants.remove(expectant);
				room.release(expectant);
				request.setCondition(RequestsConditions.DIDNOT_CALL_ELEVATOR);
			} else if (request.getCallFloor().equals(currentFloor) && request.isCallElevator()) {
				if (room.admit(expectant)) {
					request.setCondition(RequestsConditions.STAND_IN_ELEVATOR);
				} else {
					request.setCondition(RequestsConditions.TRY_CALL_AGAIN_ELEVATOR);
					expectants.remove(expectant);
				}
			}
		}
	}

	@Override
	public IAutomate getElevatorAutomate() {
		return elevatorCondition.getElevatorAutomate();
	}

	@Override
	public IFloorsRange getFloorsRange() {
		return elevatorCondition.getFloorsRange();
	}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		long serialVersionUID = stream.readLong();
		elevatorCondition = (T) stream.readObject();
		requestCounterId = (Integer) stream.readObject();
		room = (IRoom<U>) stream.readObject();
		expectants = new ConcurrentSkipListSet<>();
		getElevatorAutomate().onStop(this::stop);
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeLong(serialVersionUID);
		stream.writeObject(elevatorCondition);
		stream.writeObject(requestCounterId);
		stream.writeObject(room);
	}
}

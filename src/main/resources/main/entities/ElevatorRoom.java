package main.entities;

import main.entities.constants.PersonsConditions;
import main.entities.interfaces.IAutomobileElevatorRoom;
import main.entities.interfaces.primitive.*;
import main.entities.primitive.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * view for object implementing IAutomobileElevator,IElevatorController
 * can work with basement floors
 * this class also keep information about people
 * all methods which change room state is synchronized
 *
 * @param <T>
 */
public class ElevatorRoom<T extends IElevatorController & IAutomobileElevator & Serializable> implements IAutomobileElevatorRoom, Serializable {

	static final long serialVersionUID = -1000000000000L;

	private Integer counterPeopleId = 0;
	private T elevatorCondition;
	//не сохраняю т.к пока нет механизма чтобы выгнать из лифта если пользователь нехочет выходить
	//чтобы не испортить объетк при загрузке из хранилища
	private Map<Integer, Request> requests;
	private IRoom room;

	public ElevatorRoom(T elevatorCondition, IRoom room) {
		requests = new ConcurrentHashMap<>();
		this.room = room;
		this.elevatorCondition = elevatorCondition;
		this.elevatorCondition.getElevatorAutomate().onStop(this::stop);
	}

	@Override
	public synchronized Integer callElevator(int floor) {
		if (elevatorCondition.callup(floor)) {
			Integer requestId = counterPeopleId++;
			Request request = new Request(requestId, floor);
			request.setCondition(PersonsConditions.CALLED_ELEVATOR);
			requests.put(requestId, request);
			return requestId;
		}
		return null;
	}

	@Override
	public Integer getCurrentFloor() {
		return elevatorCondition.getCurrentFloor();
	}

	@Override
	public synchronized Boolean sendElevator(int floor, int requestId) {
		if (isCondition(requestId, PersonsConditions.STAND_IN_ELEVATOR)
				&& elevatorCondition.callup(floor)) {
			Request request = requests.get(requestId);
			request.setSendFloor(floor);
			request.setCondition(PersonsConditions.SENDED_ELEVATOR);
			return true;
		}
		return false;
	}

	@Override
	public boolean isInElevator(Integer requestId) {
		return isCondition(requestId, PersonsConditions.STAND_IN_ELEVATOR);
	}

	@Override
	public boolean isCallElevator(Integer requestId) {
		return isCondition(requestId, PersonsConditions.CALLED_ELEVATOR);
	}

	@Override
	public boolean isSendElevator(Integer requestId) {
		return isCondition(requestId, PersonsConditions.SENDED_ELEVATOR);
	}

	private boolean isCondition(Integer requestId, IConditionable condition) {
		return requestId != null && requests.containsKey(requestId) && requests.get(requestId).getCondition() == condition;
	}

	@Override
	public IConditionable getPersonCondition(Integer requestId) {
		if (requestId != null && requests.containsKey(requestId)) {
			return requests.get(requestId).getCondition();
		}
		return PersonsConditions.DIDNOT_CALL_ELEVATOR;
	}

	private synchronized void stop() {
		Integer currentFloor = elevatorCondition.getCurrentFloor();
		for (Request request : requests.values()) {
			Integer requestId = request.getId();
			if (request.getSendFloor() == currentFloor) {
				requests.remove(requestId);
				room.release(request);
				request.setCondition(PersonsConditions.DIDNOT_CALL_ELEVATOR);
			} else if (request.getCallFloor() == currentFloor && request.getCondition() == PersonsConditions.CALLED_ELEVATOR) {
				if (room.admit(request)) {
					request.setCondition(PersonsConditions.STAND_IN_ELEVATOR);
				} else {
					request.setCondition(PersonsConditions.TRY_CALL_AGAIN_ELEVATOR);
					if (elevatorCondition.callup(request.getCallFloor())) {
						request.setCondition(PersonsConditions.CALLED_ELEVATOR);
					}
				}
			}
		}
	}

	@Override
	public IElevatorAutomate getElevatorAutomate() {
		return elevatorCondition.getElevatorAutomate();
	}

	@Override
	public IFloorsRange getFloorsRange() {return  elevatorCondition.getFloorsRange();}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		long serialVersionUID = stream.readLong();
		elevatorCondition = (T) stream.readObject();
		counterPeopleId = (Integer) stream.readObject();
		room = (IRoom) stream.readObject();
		requests = new ConcurrentHashMap<>();
		getElevatorAutomate().onStop(this::stop);
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeLong(serialVersionUID);
		stream.writeObject(elevatorCondition);
		stream.writeObject(counterPeopleId);
		stream.writeObject(room);

	}
}

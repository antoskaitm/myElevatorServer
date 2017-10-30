package main.entities;

import main.entities.constants.RequestsConditions;
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

	private Integer requestCounterId = 0;
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
	public synchronized Request callElevator(int floor) {
		if (elevatorCondition.callup(floor)) {
			Integer requestId = requestCounterId++;
			Request request = new Request(requestId, floor);
			request.setCondition(RequestsConditions.CALLED_ELEVATOR);
			requests.put(requestId, request);
			return request;
		}
		return null;
	}

	@Override
	public Integer getCurrentFloor() {
		return elevatorCondition.getCurrentFloor();
	}

	@Override
	public synchronized Boolean sendElevator(int floor, int requestId) {
		if (isCondition(requestId, RequestsConditions.STAND_IN_ELEVATOR)
				&& elevatorCondition.callup(floor)) {
			Request request = requests.get(requestId);
			request.setSendFloor(floor);
			request.setCondition(RequestsConditions.SENDED_ELEVATOR);
			return true;
		}
		return false;
	}

	@Override
	public boolean isInElevator(Integer requestId) {
		return isCondition(requestId, RequestsConditions.STAND_IN_ELEVATOR);
	}

	@Override
	public boolean isCallElevator(Integer requestId) {
		return isCondition(requestId, RequestsConditions.CALLED_ELEVATOR);
	}

	@Override
	public boolean isSendElevator(Integer requestId) {
		return isCondition(requestId, RequestsConditions.SENDED_ELEVATOR);
	}

	private boolean isCondition(Integer requestId, IConditionable condition) {
		return requestId != null && requests.containsKey(requestId) && requests.get(requestId).getCondition() == condition;
	}

	@Override
	public IConditionable getRequestCondition(Integer requestId) {
		if (requestId != null && requests.containsKey(requestId)) {
			return requests.get(requestId).getCondition();
		}
		return RequestsConditions.DIDNOT_CALL_ELEVATOR;
	}

	private synchronized void stop() {
		Integer currentFloor = elevatorCondition.getCurrentFloor();
		for (Request request : requests.values()) {
			Integer requestId = request.getId();
			if (request.getSendFloor() == currentFloor) {
				requests.remove(requestId);
				room.release(request);
				request.setCondition(RequestsConditions.DIDNOT_CALL_ELEVATOR);
			} else if (request.getCallFloor() == currentFloor && request.getCondition() == RequestsConditions.CALLED_ELEVATOR) {
				if (room.admit(request)) {
					request.setCondition(RequestsConditions.STAND_IN_ELEVATOR);
				} else {
					request.setCondition(RequestsConditions.TRY_CALL_AGAIN_ELEVATOR);
					requests.remove(requestId);
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
		requestCounterId = (Integer) stream.readObject();
		room = (IRoom) stream.readObject();
		requests = new ConcurrentHashMap<>();
		getElevatorAutomate().onStop(this::stop);
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeLong(serialVersionUID);
		stream.writeObject(elevatorCondition);
		stream.writeObject(requestCounterId);
		stream.writeObject(room);
	}
}

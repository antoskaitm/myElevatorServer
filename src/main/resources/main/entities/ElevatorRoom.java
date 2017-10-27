package main.entities;

import main.entities.interfaces.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

/**view for object implementing IElevatorAutomateble,IElevatorUi
 * can work with basement floors
 * this class also keep information about people
 * all methods which change room state is synchronized
 * @param <T>
 */
public class ElevatorRoom<T extends IElevatorUi&IElevatorAutomateble&Serializable> implements IElevatorRoom, IElevatorAutomateble , Serializable {

    static final long serialVersionUID = -1000000000000L;

    private Integer counterPeopleId = 0;
    private T elevatorCondition;
    private Map<Integer, Queue<Integer>> sendElevatorPersons = new ConcurrentHashMap<>();
    private Map<Integer, Queue<Integer>> callElevatorPersons = new ConcurrentHashMap<>();
    private Set<Integer> personsInLift = new ConcurrentSkipListSet<>();

    public ElevatorRoom(T elevatorCondition) {
        this.elevatorCondition = elevatorCondition;
        this.elevatorCondition.getElevatorAutomate().onStop(this::stop);
    }

    @Override
    public synchronized Integer callElevator(int floor) {
        if (!getQueue(callElevatorPersons, floor).isEmpty() || elevatorCondition.callup(floor)) {
            Integer personId = counterPeopleId++;
            getQueue(callElevatorPersons, floor).add(personId);
            return personId;
        }
        return null;
    }

    @Override
    public Integer getCurrentFloor() {
        return elevatorCondition.getCurrentFloor();
    }

    @Override
    public synchronized Boolean sendElevator(int floor, int personId) {
        if (elevatorCondition.getCurrentFloor().equals(floor)) {
            return false;
        }
        if (personsInLift.contains(personId)) {
            if (elevatorCondition.callup(floor)) {
                getQueue(sendElevatorPersons, floor).add(personId);
                personsInLift.remove(personId);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInElevator(Integer personId) {
        return personsInLift.contains(personId);
    }

    private boolean contains(Map<Integer, Queue<Integer>> map, Integer personId) {
        for (Queue<Integer> waits : map.values()) {
            if (waits.contains(personId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCallElevator(Integer personId) {
        return contains(callElevatorPersons, personId);
    }

    @Override
    public boolean isSendElevator(Integer personId) {
        return contains(sendElevatorPersons, personId);
    }

    @Override
    public IElevatorAutomate getElevatorAutomate() {
        return elevatorCondition.getElevatorAutomate();
    }

    @Override
    public IPersonCondition getPersonCondition(Integer personId) {
        if (personId == null) {
            return PersonCondition.DIDNOT_CALL_ELEVATOR;
        }
        if (personsInLift.contains(personId)) {
            return PersonCondition.STAND_IN_ELEVATOR;
        }
        if (contains(callElevatorPersons, personId)) {
            return PersonCondition.CALLED_ELEVATOR;
        }
        if (contains(sendElevatorPersons, personId)) {
            return PersonCondition.SENDED_ELEVATOR;
        }
        return PersonCondition.DIDNOT_CALL_ELEVATOR;
    }

    private Queue<Integer> getQueue(Map<Integer, Queue<Integer>> map, int floor) {
        if (!map.containsKey(floor)) {
            map.put(floor, new ConcurrentLinkedQueue<>());
        }
        return map.get(floor);
    }

    private synchronized void stop() {
        Integer floor = elevatorCondition.getCurrentFloor();
        this.personsInLift.addAll(getQueue(callElevatorPersons, floor));
        getQueue(callElevatorPersons, floor).clear();
        getQueue(sendElevatorPersons, floor).clear();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        long serialVersionUID = stream.readLong();
        counterPeopleId = (Integer) stream.readObject();
        elevatorCondition = (T) stream.readObject();
        sendElevatorPersons = (Map<Integer, Queue<Integer>>) stream.readObject();
        callElevatorPersons = new ConcurrentHashMap<>();
        personsInLift = new ConcurrentSkipListSet<>();
        elevatorCondition.getElevatorAutomate().onStop(this::stop);
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeLong(serialVersionUID);
        stream.writeObject(counterPeopleId);
        stream.writeObject(elevatorCondition);
        stream.writeObject(sendElevatorPersons);
    }
}

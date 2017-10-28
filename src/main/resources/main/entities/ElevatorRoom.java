package main.entities;

import main.entities.constants.PersonCondition;
import main.entities.interfaces.*;
import main.entities.interfaces.primitive.IElevatorAutomate;
import main.entities.interfaces.primitive.IElevatorAutomateble;
import main.entities.interfaces.primitive.IElevatorUi;
import main.entities.interfaces.primitive.IPersonCondition;
import main.entities.primitive.Person;

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
public class ElevatorRoom<T extends IElevatorUi &IElevatorAutomateble &Serializable> implements IAutomobileElevatorRoom, Serializable {

    static final long serialVersionUID = -1000000000000L;

    private Integer counterPeopleId = 0;
    private T elevatorCondition;
    private Map<Integer, Person> persons;

    public ElevatorRoom(T elevatorCondition) {
        persons = new ConcurrentHashMap<>();
        this.elevatorCondition = elevatorCondition;
        this.elevatorCondition.getElevatorAutomate().onStop(this::stop);
    }

    @Override
    public synchronized Integer callElevator(int floor) {
        if (elevatorCondition.callup(floor)) {
            Integer personId = counterPeopleId++;
            Person person = new Person(personId, floor);
            person.setCondition(PersonCondition.CALLED_ELEVATOR);
            persons.put(personId, person);
            return  personId;
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
        if (isCondition(personId, PersonCondition.STAND_IN_ELEVATOR)
                && elevatorCondition.callup(floor)) {
            Person person = persons.get(personId);
            person.setSendFloor(floor);
            person.setCondition(PersonCondition.SENDED_ELEVATOR);
            return true;
        }
        return false;
    }

    @Override
    public boolean isInElevator(Integer personId) {
        return isCondition(personId, PersonCondition.STAND_IN_ELEVATOR);
    }

    @Override
    public boolean isCallElevator(Integer personId) {

        return isCondition(personId, PersonCondition.CALLED_ELEVATOR);
    }

    @Override
    public boolean isSendElevator(Integer personId) {
        return isCondition(personId, PersonCondition.SENDED_ELEVATOR);
    }

    private boolean isCondition(Integer personId, IPersonCondition condition) {
        return personId != null && persons.containsKey(personId) && persons.get(personId).getCondition() == condition;
    }

    @Override
    public IPersonCondition getPersonCondition(Integer personId) {
        if (personId != null && persons.containsKey(personId)) {
            return persons.get(personId).getCondition();
        }
        return PersonCondition.DIDNOT_CALL_ELEVATOR;
    }

    private synchronized void stop() {
        Integer currentFloor = elevatorCondition.getCurrentFloor();
        for (Person person : persons.values()) {
            Integer personId = person.getId();
            if (person.getSendFloor() == currentFloor) {
                persons.remove(personId);
                person.setCondition(PersonCondition.DIDNOT_CALL_ELEVATOR);
            } else if (person.getCallFloor() == currentFloor) {
                person.setCondition(PersonCondition.STAND_IN_ELEVATOR);
            }
        }
    }

    @Override
    public IElevatorAutomate getElevatorAutomate() {
        return elevatorCondition.getElevatorAutomate();
    }
}

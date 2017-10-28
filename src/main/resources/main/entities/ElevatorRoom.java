package main.entities;

import main.entities.constants.PersonsConditions;
import main.entities.interfaces.*;
import main.entities.interfaces.primitive.*;
import main.entities.primitive.Person;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
    private IRoom room;

    public ElevatorRoom(T elevatorCondition,IRoom room) {
        persons = new ConcurrentHashMap<>();
        this.room = room;
        this.elevatorCondition = elevatorCondition;
        this.elevatorCondition.getElevatorAutomate().onStop(this::stop);
    }

    @Override
    public synchronized Integer callElevator(int floor) {
        if (elevatorCondition.callup(floor)) {
            Integer personId = counterPeopleId++;
            Person person = new Person(personId, floor);
            person.setCondition(PersonsConditions.CALLED_ELEVATOR);
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
        if (isCondition(personId, PersonsConditions.STAND_IN_ELEVATOR)
                && elevatorCondition.callup(floor)) {
            Person person = persons.get(personId);
            person.setSendFloor(floor);
            person.setCondition(PersonsConditions.SENDED_ELEVATOR);
            return true;
        }
        return false;
    }

    @Override
    public boolean isInElevator(Integer personId) {
        return isCondition(personId, PersonsConditions.STAND_IN_ELEVATOR);
    }

    @Override
    public boolean isCallElevator(Integer personId) {

        return isCondition(personId, PersonsConditions.CALLED_ELEVATOR);
    }

    @Override
    public boolean isSendElevator(Integer personId) {
        return isCondition(personId, PersonsConditions.SENDED_ELEVATOR);
    }

    private boolean isCondition(Integer personId, IConditionable condition) {
        return personId != null && persons.containsKey(personId) && persons.get(personId).getCondition() == condition;
    }

    @Override
    public IConditionable getPersonCondition(Integer personId) {
        if (personId != null && persons.containsKey(personId)) {
            return persons.get(personId).getCondition();
        }
        return PersonsConditions.DIDNOT_CALL_ELEVATOR;
    }

    private synchronized void stop() {
        Integer currentFloor = elevatorCondition.getCurrentFloor();
        for (Person person : persons.values()) {
            Integer personId = person.getId();
            if (person.getSendFloor() == currentFloor) {
                persons.remove(personId);
                room.release(person);
                person.setCondition(PersonsConditions.DIDNOT_CALL_ELEVATOR);
            } else if (person.getCallFloor() == currentFloor && person.getCondition() == PersonsConditions.CALLED_ELEVATOR) {
                if (room.admit(person)) {
                    person.setCondition(PersonsConditions.STAND_IN_ELEVATOR);
                } else {
                    person.setCondition(PersonsConditions.TRY_CALL_AGAIN_ELEVATOR);
                    if (elevatorCondition.callup(person.getCallFloor())) {
                        person.setCondition(PersonsConditions.CALLED_ELEVATOR);
                    }
                }
            }
        }
    }

    @Override
    public IElevatorAutomate getElevatorAutomate() {
        return elevatorCondition.getElevatorAutomate();
    }
}

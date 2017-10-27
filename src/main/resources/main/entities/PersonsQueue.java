package main.entities;

import main.entities.interfaces.*;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class PersonsQueue <T extends IElevatorUi &IElevatorAutomateble &Serializable> implements IAutomobileElevatorRoom, Serializable {

    private IAutomobileElevatorRoom room;
    private Map<Integer, Person> persons;

    public PersonsQueue(IAutomobileElevatorRoom room) {
        this.room = room;
        room.getElevatorAutomate().onStop(this::stop);
        persons = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized Integer callElevator(int floor) {
        Integer id = room.callElevator(floor);
        if (id != null) {
            Person person = new Person(id, floor);
            person.setCondition(PersonCondition.CALLED_ELEVATOR);
            persons.put(id, person);
        }
        return id;
    }

    @Override
    public Integer getCurrentFloor() {
        return room.getCurrentFloor();
    }

    @Override
    public synchronized Boolean sendElevator(int floor, int personId) {
        if (isCondition(personId, PersonCondition.STAND_IN_ELEVATOR)
                && room.sendElevator(floor, personId)) {
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

    public Person getPerson(Integer personId) {
        return persons.get(personId);
    }

    private synchronized void stop() {
        Integer currentFloor = room.getCurrentFloor();
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
        return room.getElevatorAutomate();
    }
}
